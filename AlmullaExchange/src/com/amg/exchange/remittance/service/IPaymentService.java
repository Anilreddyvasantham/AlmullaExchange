package com.amg.exchange.remittance.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.remittance.model.PaymentMode;
import com.amg.exchange.remittance.model.PaymentModeDesc;

public interface IPaymentService {
	public void save(PaymentMode  paymentMode);

	public void saveRecord(PaymentModeDesc paymentModeDesc);
	
	public List<PaymentMode> searchRecord(String paymentcode);

	public List<PaymentModeDesc> paymentDescRec(BigDecimal paymentId);
	
	public List<String> getPaymntcodelist(String qry);
	
	public List<PaymentMode> getPayment();
	
	public String getPaymentDesc(BigDecimal arbId);
	
	public String getCreatedBy();
	//added by nazish for view
	public List<PaymentModeDesc> viewRecord();
	
	public List<PaymentMode> getPaymentMode();
	

	public BigDecimal getPaymentPk(String englang);
	public BigDecimal getPaymentarbPk(String arblang);
	
	public String getArbPaymnetName(BigDecimal paymentModeId);

	public List<PaymentModeDesc> getListOfPayment();

	//added koti
	public List<PaymentMode> getPaymentCheck(String paymentcode);

	public String getPaymentDisc(BigDecimal engid);
	public void delete(PaymentMode paymentMode);
	public void delete(PaymentModeDesc paymentModeDesc);
	
	public List<PaymentModeDesc> fetchPaymodeDesc(BigDecimal langId,String isActive);

	public BigDecimal fetchPaymodeMasterId(String paymentModeDesc,BigDecimal langId);

	//Desc Added Koti
	public List<PaymentModeDesc> getRecordsFromDb(BigDecimal paymentModeId);

	public void deleteRecordPermrntely(BigDecimal dtpymPk,BigDecimal dtpymDescpken, BigDecimal dtpymDescpkarb);
	
	
	public List<PaymentModeDesc> getPaymentDescLangList(BigDecimal languageId);
	public String approveRecord(BigDecimal paymentModePk,String userName);
	
	public String paymentModeDescription(BigDecimal paymentId , BigDecimal langId);
	public List<PaymentMode>  getAllRecordsFrmPaymentMode();
	public List<PaymentModeDesc> getAllRecordsBasedOnPaymentModeId(BigDecimal paymentModeId);
	public List<PaymentModeDesc> fetchPaymodeDescForStopPayment(BigDecimal langId,String isActive) ;
	
	public List<PaymentModeDesc> searchRecordByCode(String paymentcode);
	
}
