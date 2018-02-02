package com.amg.exchange.online.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.amg.exchange.online.model.RatePlaceOrder;
import com.amg.exchange.remittance.model.RemittanceApplication;

public interface IPlaceOrderPendingTransctionService {

	public List<RatePlaceOrder> toFetchAllRecordsFromDb(BigDecimal customerId);

	public BigDecimal toFetchPaymentId(String collectionMode);

	public String toFetchPaymentName(BigDecimal paymentId, BigDecimal languageId);
	
	public HashMap<String, Object>  updatePlaceOrderPaymentDetails(HashMap<String, Object> inputValues);
	public HashMap<String, String> placeOrderRemitTranxProcedure(HashMap<String, Object> inputValues) throws Exception;
	
	public List<RemittanceApplication> getTransactionDetails(BigDecimal ratePlaceOrderId);

}
