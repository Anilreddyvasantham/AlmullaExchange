package com.amg.exchange.wuh2h.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

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
import com.amg.exchange.wuh2h.model.DynamicFileds;
import com.amg.exchange.wuh2h.model.DynamicPurposeCode;
import com.amg.exchange.wuh2h.model.ViewWUSendTransaction;
import com.amg.exchange.wuh2h.model.WUCorridorCountry;
import com.amg.exchange.wuh2h.model.WUCorridorCurrency;
import com.amg.exchange.wuh2h.model.WURemittanceApplicationView;
import com.amg.exchange.wuh2h.model.WUReportTermsAndConditions;

public interface IWUH2HService {
	
	public void saveCollection(Collect collection, List<CollectDetail> collectionDetailList);
	
	public BigDecimal saveTempCollectionwithDetailsandTempCurrencyAdjust(TempCollection tempCollection, List<TempCollectDetail> tempDetailsList, List<TempForeignCurrencyAdjust> tempAdjustList) throws Exception;
	
	public String getNameDescription(BigDecimal id, BigDecimal languageId, String option);
	
	public void saveAllApplTransaction(HashMap<String, Object> mapAllDetailPersonalRemittanceApplSave) throws AMGException;
	
	public  List<CurrencyMaster> getWUh2HCurrencyList(BigDecimal applicationCurrencyId, BigDecimal customerCurrencyId);
	public BigDecimal  getRemitModeForCash();
	
	public List<ReceiveReceiptView> getReceiveReceiptData(BigDecimal collectionDocumentNo, BigDecimal collectionDocumentFinanceYear,BigDecimal collectionDocumentCode,BigDecimal companyId,BigDecimal applicatioCountryId);
	
	public List<WUStateCityModel> getWUStateList(String countryISO);
	
	public List<WUStateCityModel> getWUCityList(String countryISO, String stateCode);
	
	public void saveCurrAdjustReceiptAndPayment(List<ForeignCurrencyAdjust> foreignCurrencyList,ReceiptPayment receiptPaymnet, List<Payment> payment) throws Exception;
	
	public List<WUTermsAndConditions> getWUTermsAndConditions();
	
	public String getWUEnrollReference(BigDecimal customerId);
	public BigDecimal getCountryIDFromCode(String countryCode);
	public String getCountryName(BigDecimal languageId, BigDecimal countryId);
	public int updateWUEnrollCardNo(BigDecimal customerId,String wucardno);
	
	public List<WUCorridorCountry> getWUDynamicFieldCountry();
	
	public List<DynamicFileds> getWUDynamicFields(String isoCountry);
	public List<DynamicPurposeCode> getWUDynamicPurposeCode(String isoCountry);
	
	public List<WUCorridorCurrency> getWUCorridorCurrency(String isoCountry);
	
	public String getNextReferenceNo();
	public List<RemittanceApplication> getRemittanceAppBasedonMtcNo(String mtcNo);
	public void updateWuSendMoneyStoreSts(String mtcNo,String newpoints);
	public List<ViewWUSendTransaction> getWUTxnInquiryList(BigDecimal customerNo,BigDecimal companyId,BigDecimal applicationCountryId, BigDecimal documentCode, BigDecimal documentFinanceYear);
	public List<ReceiveReceiptView> getWUReceiveTxnInquiryList(BigDecimal customerNo,BigDecimal companyId,BigDecimal applicationCountryId, BigDecimal documentCode, BigDecimal documentFinanceYear);
	public HashMap<String, Object> saveAllRemittanceTransaction(HashMap<String, Object> mapAllDetailPersonalRemittanceSave) throws AMGException;
	
	public List<WURemittanceApplicationView> getRecordsForWURemittanceReceiptReport(BigDecimal documentNo,BigDecimal financeYear,String documentCode, BigDecimal applicationCountryId, BigDecimal countryId);
	
	public List<ViewWUSendTransaction> getWUSendTxnInquiryListByTerminal(BigDecimal companyId,BigDecimal applicationCountryId, BigDecimal documentCode, BigDecimal documentFinanceYear,String terminalAddress);
	
	public List<ReceiveReceiptView> getWUReceiveTxnInquiryListByTerminal(BigDecimal companyId,BigDecimal applicationCountryId, BigDecimal documentCode, BigDecimal documentFinanceYear,String terminalAddress);
	
	public List<WUReportTermsAndConditions> getWUReportTermsAndConditions();
	
	public List<ReceiptPayment> getReceiveMtcNo(String mtcNo);
	
	public String saveReceivetoOldByProc(BigDecimal countryId, BigDecimal companyId, BigDecimal documentId, BigDecimal financialYear,BigDecimal documentno);
	public List<RemittanceTransaction> getRemittanceTrnxBasedonMtcNo(String mtcNo);
}
