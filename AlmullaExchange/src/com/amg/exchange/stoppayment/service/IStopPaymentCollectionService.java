package com.amg.exchange.stoppayment.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.amg.exchange.cancelreissue.model.RemittanceView;
import com.amg.exchange.foreigncurrency.model.ReceiptPayment;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.remittance.model.RemittanceApplication;
import com.amg.exchange.stoppayment.model.RemittanceComplaint;
import com.amg.exchange.stoppayment.model.RemittanceTransaction;
import com.amg.exchange.treasury.viewModel.CurrencyMasterView;
import com.amg.exchange.util.AMGException;

public interface IStopPaymentCollectionService {
	public RemittanceApplication viewBean(BigDecimal transferNo);

	public RemittanceTransaction getTransactiondetailsbyDocumentNo(Long transferNo);

	public String getProductName(BigDecimal bankId, BigDecimal remittanceModeId, BigDecimal deliveryModeId, BigDecimal corespondingCountryId, BigDecimal foreignCurrencyId);

	public List<Customer> getCustomerInfo(BigDecimal customerId);

	public List<RemittanceTransaction> getRemittanceTransaction(BigDecimal countryId, Integer finYear, BigDecimal documentCode, BigDecimal companyId, BigDecimal documentId);

	public RemittanceView getRemittanceTransactionFromView(BigDecimal countryId, Integer finYear, BigDecimal documentCode, BigDecimal companyId, BigDecimal documentId);

	public BigDecimal getRemittanceId(String cashProduct);

	public BigDecimal getBanKId(String WU);

	public void updateReceiptPaymentTableData(BigDecimal receiptPaymentPk);

	public BigDecimal getReceiptPaymentTablePk(BigDecimal customerId, BigDecimal documentNo);

	public void updateRemittanceCompliantTableData(BigDecimal receiptPaymentPk);

	public BigDecimal getRemittanceCompliantPk(BigDecimal documentNo);

	public BigDecimal getRemittanceId(BigDecimal languageId, String cashProduct);

	public List<CountryBranch> getRemittanceTransaction(BigDecimal countryId);

	public List<RemittanceTransaction> getHighValueCusotmerList(BigDecimal branchId, String isActive);

	public void updateRemittanceTransaction(List<BigDecimal> remittanceTransactionId, String userName);

	public void saveAlltheDeatailsforCash(HashMap<String, Object> mapAllDetailForSave) throws Exception;

	public void saveAlltheDeatailsforCard(HashMap<String, Object> mapAllDetailForSave) throws Exception;

	public void updateTransactiondetailsbyTransactionId(BigDecimal remittanceTransactionId, String status, String userName);

	public String getCompanyName(BigDecimal companyId, BigDecimal languageId);

	public boolean checkRemittanceCancelationStatus(BigDecimal companyId, BigDecimal documentId, BigDecimal documentCode, BigDecimal documentNo, BigDecimal caneDocFinYear);

	public List<CurrencyMasterView> getCurrencyDetails(BigDecimal currencyId);

	public List<ReceiptPayment> checkRecieptPaymentExist(BigDecimal transferYear, BigDecimal TransferrefNo);

	public void saveOrUpDateAllValues(HashMap<String, Object> inputValues) throws AMGException;

	public BigDecimal toFetchRemitTrnxPk(BigDecimal companyId, BigDecimal countryId, BigDecimal documentId, BigDecimal documentNum, BigDecimal docFinYear);

	public BigDecimal toFetchDocumentPk(BigDecimal documentCode);
	
	public HashMap<String, Object> toFetchRefundDetails(HashMap<String, Object> inputParamters) throws AMGException;
	
	public List<RemittanceComplaint> checkRemittanceComplaintExist(BigDecimal transferYear, BigDecimal TransferrefNo);
	
	public List<RemittanceComplaint> getRefundRequestExist(BigDecimal companyID,BigDecimal documentID,BigDecimal docNumber,BigDecimal docFinanceYearId,String cancelStatus);
	
	public BigDecimal roundingTotalNetAmountbyFunction(BigDecimal applcountryId, BigDecimal totalNetAmount, String roundstatus) throws AMGException;
	
	public void saveOrUpdate(BigDecimal companyID,BigDecimal documentID,BigDecimal docNumber,BigDecimal docFinanceYearId,String cancelStatus,BigDecimal remittanceYear,String username);	
	
}
