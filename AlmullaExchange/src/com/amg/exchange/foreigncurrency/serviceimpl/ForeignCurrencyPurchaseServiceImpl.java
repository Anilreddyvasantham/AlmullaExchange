package com.amg.exchange.foreigncurrency.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.foreigncurrency.dao.IForeignCurrencyPurchaseDao;
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
import com.amg.exchange.foreigncurrency.service.IForeignCurrencyPurchaseService;
import com.amg.exchange.miscellaneous.model.Payment;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.remittance.model.CashRate;
import com.amg.exchange.remittance.model.PaymentMode;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.util.AMGException;

@Service("foreignCurrencyPurchaseServiceImpl")
public class ForeignCurrencyPurchaseServiceImpl<T> implements IForeignCurrencyPurchaseService<T>, Serializable {
	private static final long serialVersionUID = 1L;
	@Autowired
	IForeignCurrencyPurchaseDao<T> foreignCurrencyPurchaseDao;

	public IForeignCurrencyPurchaseDao<T> getForeignCurrencyPurchaseDao() {
		return foreignCurrencyPurchaseDao;
	}

	public void setForeignCurrencyPurchaseDao(IForeignCurrencyPurchaseDao<T> foreignCurrencyPurchaseDao) {
		this.foreignCurrencyPurchaseDao = foreignCurrencyPurchaseDao;
	}

	@Override
	@Transactional
	public List<CurrencyMaster> getAllCurrency(BigDecimal contyId) {
		return getForeignCurrencyPurchaseDao().getAllCurrency(contyId);
	}

	@Override
	@Transactional
	public List<CurrencyWiseDenomination> getDenominationByCurrencyID(BigDecimal currencyId) {
		return getForeignCurrencyPurchaseDao().getDenominationByCurrencyID(currencyId);
	}

	@Override
	@Transactional
	public List<SourceOfIncome> getAllSourceOfIncome() {
		return getForeignCurrencyPurchaseDao().getAllSourceOfIncome();
	}

	@Override
	@Transactional
	public List<PurposeOfTransaction> getAllPurposeOfTransaction() {
		return getForeignCurrencyPurchaseDao().getAllPurposeOfTransaction();
	}

	@Override
	@Transactional
	public List<CustomerIdProof> dataCust(String id) {
		return getForeignCurrencyPurchaseDao().dataCust(id);
	}

	@Override
	@Transactional
	public void save(T entity) throws AMGException{
		try{
		getForeignCurrencyPurchaseDao().save(entity);
		}catch(Exception e){
			throw new AMGException(e.getMessage());
		}
	}

	@Override
	@Transactional
	public void saveOrUpdate(T entity) throws AMGException{
		getForeignCurrencyPurchaseDao().saveOrUpdate(entity);
	}

	@Override
	@Transactional
	public void delete(T entity) {
		getForeignCurrencyPurchaseDao().delete(entity);
	}

	@Override
	@Transactional
	public void saveCollect(Collect collect) throws AMGException{
		getForeignCurrencyPurchaseDao().saveCollect(collect);
	}

	@Override
	@Transactional
	public List<UserFinancialYear> getUserFinancialYear(Date currentDate) {
		return getForeignCurrencyPurchaseDao().getUserFinancialYear(currentDate);
	}

	@Override
	@Transactional
	public List<CurrencyMaster> getCurrencyById(BigDecimal currencyId) {
		return getForeignCurrencyPurchaseDao().getCurrencyById(currencyId);
	}

	@Override
	@Transactional
	public List<String> getFsAvg(Object obj, BigDecimal currencyId) {
		return getForeignCurrencyPurchaseDao().getFsAvg(obj, currencyId);
	}

	/*@Override
	@Transactional
	public String getNextDocumentSerialNumber(int countryId, int companyId, int documentId, int financialYear, String processIn) {
		return getForeignCurrencyPurchaseDao().getNextDocumentSerialNumber(countryId, companyId, documentId, financialYear, processIn);
	}*/

	// Rolewise exchange rate by subramanian 04-Dec-2014
	@Override
	@Transactional
	public List<CashRate> getRoleWiseExchangeRateByRoleId(BigDecimal locationId,BigDecimal saleCurrencyId) {
		return getForeignCurrencyPurchaseDao().getRoleWiseExchangeRateByRoleId(locationId, saleCurrencyId);
	}

	@Override
	@Transactional
	public List<String> getBetweenRolewiseExchangeRate(Object obj, BigDecimal roleId) {
		System.out.println("Service In --------------------  = ------------------------>");
		return getForeignCurrencyPurchaseDao().getBetweenRolewiseExchangeRate(obj, roleId);
	}

	@Override
	@Transactional
	public String getSignature(BigDecimal id) {
		return getForeignCurrencyPurchaseDao().getSignature(id);
	}

	@Override
	@Transactional
	public List<CurrencyWiseDenomination> getDenominationByCountryIDCurrencyID(BigDecimal countryId, BigDecimal currencyId) {
		return getForeignCurrencyPurchaseDao().getDenominationByCountryIDCurrencyID(countryId, currencyId);
	}

	@Override
	@Transactional
	public List<ForeignCurrencyAdjustApp> getAllValuesForReportGenaration(BigDecimal customerIdNumber, String documentId, BigDecimal docYear) {
		return getForeignCurrencyPurchaseDao().getAllValuesForReportGenaration(customerIdNumber, documentId, docYear);
	}

	@Override
	@Transactional
	public List<ReceiptPaymentApp> getReceiptPaymentForReportGeneration(BigDecimal documentNo) {
		return getForeignCurrencyPurchaseDao().getReceiptPaymentForReportGeneration(documentNo);
	}

	@Override
	@Transactional
	public BigDecimal getLocalCurrencyId(BigDecimal countryId) {
		return getForeignCurrencyPurchaseDao().getLocalCurrencyId(countryId);
	}

	@Override
	@Transactional
	public List<ForeignCurrencyPurchaseReport> getFcPurchaseReportList(BigDecimal appcountryId, BigDecimal companyId, BigDecimal documentcode, BigDecimal financialYr, BigDecimal documentNo) {
		return getForeignCurrencyPurchaseDao().getFcPurchaseReportList(appcountryId, companyId, documentcode, financialYr, documentNo);
	}

	@Override
	@Transactional
	public List<CollectDetail> getCustomerEnquiry(BigDecimal documentYear, BigDecimal documentNo) {
		return getForeignCurrencyPurchaseDao().getCustomerEnquery(documentYear, documentNo);
	}

	@Override
	@Transactional
	public List<UserFinancialYear> getAllDocumentYear() {
		return getForeignCurrencyPurchaseDao().getAllDocumentYear();
	}

	@Override
	@Transactional
	public List<Customer> getCustomerAllDetails(BigDecimal customerId) {
		return getForeignCurrencyPurchaseDao().getCustomerAllDetails(customerId);
	}

	@Override
	@Transactional
	public List<ReceiptPayment> getReceipetData(BigDecimal documentFinanceYear, BigDecimal documentNo) {
		return getForeignCurrencyPurchaseDao().getReceipetData(documentFinanceYear, documentNo);
	}

	@Override
	@Transactional
	public List<ReceiptPaymentApp> getReceipetDataApp(BigDecimal documentFinanceYear, BigDecimal documentNo) {
		return getForeignCurrencyPurchaseDao().getReceipetDataApp(documentFinanceYear, documentNo);
	}

	@Override
	@Transactional
	public List<ForeignCurrencyAdjustApp> getCurrencyAdjustRecords(BigDecimal documentFinanceYear, BigDecimal documentNo) {
		return getForeignCurrencyPurchaseDao().getCurrencyAdjustRecords(documentFinanceYear, documentNo);
	}

	@Override
	@Transactional
	public List<SourceOfIncomeDescription> getSourceofIncome(BigDecimal languageId) {
		return getForeignCurrencyPurchaseDao().getSourceofIncome(languageId);
	}

	@Override
	@Transactional
	public List<CollectDetail> getFCPurchageCollectionDetails(String documentCode, BigDecimal documentFinanceYear, BigDecimal documentNo) {
		return getForeignCurrencyPurchaseDao().getFCPurchageCollectionDetails(documentCode,documentFinanceYear,documentNo);
	}

	@Override
	@Transactional
	public List<ForeignCurrencyAdjust> getforeignCurrencyAdjustList(BigDecimal collectionId) {
		return getForeignCurrencyPurchaseDao().getforeignCurrencyAdjustList(collectionId);
	}

	@Override
	@Transactional
	public String getsourceofIncomeDesc(BigDecimal languageId, BigDecimal sourceId) {
		return getForeignCurrencyPurchaseDao().getsourceofIncomeDesc(languageId,sourceId);
	}

	@Override
	@Transactional
	public String getPurposeofTransaction(BigDecimal purposeId) {
		return getForeignCurrencyPurchaseDao().getPurposeofTransaction(purposeId);
	}

	@Override
	@Transactional
	public BigDecimal getcountrybasedonCurrency(BigDecimal currencyId) {
		return getForeignCurrencyPurchaseDao().getcountrybasedonCurrency(currencyId);
	}

	@Override
	@Transactional
	public String getInsertEmosFcPurchase(BigDecimal appcountryId, BigDecimal companyId, BigDecimal documentId, BigDecimal financialYr,
			BigDecimal documentNo) throws AMGException {
		return getForeignCurrencyPurchaseDao().getInsertEmosFcPurchase(appcountryId, companyId, documentId, financialYr, documentNo);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public BigDecimal saveCollectCollecDetailCurrAdjustAndFinalReceipt(Collect collection, List<CollectDetail> collectionDetailList,List<ForeignCurrencyAdjust> foreignCurrencyList,ReceiptPayment receiptPaymnet, List<Payment> payment) throws Exception {
		return getForeignCurrencyPurchaseDao().saveCollectCollecDetailCurrAdjustAndFinalReceipt(collection, collectionDetailList, foreignCurrencyList, receiptPaymnet, payment);
	}
	
	@Override
	@Transactional
	public List<PaymentMode> getPaymentModeDetails(String paymentCode){
		return getForeignCurrencyPurchaseDao().getPaymentModeDetails(paymentCode);
	}

	@Override
	@Transactional
	public List<ReceiptPaymentView> getReceiptPaymentView(
			BigDecimal documentNo, BigDecimal documentYear,
			BigDecimal companyId, BigDecimal DocumentCode) {
		return getForeignCurrencyPurchaseDao().getReceiptPaymentView(  documentNo,   documentYear,
				  companyId,  DocumentCode);
	}

	@Override
	@Transactional
	public List<CurrencyAdjustView> getCurrencyAdjustViewRecords(
			BigDecimal documentNo, BigDecimal documentYear,
			BigDecimal companyId, BigDecimal DocumentCode) {
		return getForeignCurrencyPurchaseDao().getCurrencyAdjustViewRecords(  documentNo,   documentYear,
				  companyId,   DocumentCode);
	}

	@Override
	@Transactional
	public BigDecimal getRoundedSaleAmountByFunc(BigDecimal tsaleAmount) {
		return getForeignCurrencyPurchaseDao().getRoundedSaleAmountByFunc(tsaleAmount);
	}

	@Override
	@Transactional
	public List<CustomerIdProof> fetchCustomerByIdentityInt(String idno) {
		return getForeignCurrencyPurchaseDao().fetchCustomerByIdentityInt(idno);
	}

	@Override
	@Transactional
	public String getBranchName(BigDecimal countryBranchId) {
		return getForeignCurrencyPurchaseDao().getBranchName(countryBranchId);
	}
	
}
