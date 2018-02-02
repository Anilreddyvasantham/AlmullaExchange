package com.amg.exchange.online.serviceimpl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.online.dao.IPlaceOrderPendingTransctionDao;
import com.amg.exchange.online.model.RatePlaceOrder;
import com.amg.exchange.online.service.IPlaceOrderPendingTransctionService;
import com.amg.exchange.remittance.model.RemittanceApplication;
@Service("placeOrderPendingTransctionServiceImpl")
@Transactional
public class PlaceOrderPendingTransctionServiceImpl implements IPlaceOrderPendingTransctionService{

	@Autowired
	IPlaceOrderPendingTransctionDao placeOrderPendingTransctionDao;

	@Override
	public List<RatePlaceOrder> toFetchAllRecordsFromDb(BigDecimal customerId) {
		return placeOrderPendingTransctionDao.toFetchAllRecordsFromDb(customerId);
	}

	@Override
	public BigDecimal toFetchPaymentId(String collectionMode) {
		return placeOrderPendingTransctionDao.toFetchPaymentId(collectionMode);
	}

	@Override
	public String toFetchPaymentName(BigDecimal paymentId, BigDecimal languageId) {
		return placeOrderPendingTransctionDao.toFetchPaymentName(paymentId, languageId);
	}

	@Override
	public HashMap<String, Object> updatePlaceOrderPaymentDetails(
			HashMap<String, Object> inputValues) {
		return placeOrderPendingTransctionDao.updatePlaceOrderPaymentDetails(inputValues);
		
	}

	@Override
	public HashMap<String, String> placeOrderRemitTranxProcedure(
			HashMap<String, Object> inputValues) throws Exception {
		
		return placeOrderPendingTransctionDao.placeOrderRemitTranxProcedure(inputValues);
	}

	@Override
	public List<RemittanceApplication> getTransactionDetails(
			BigDecimal ratePlaceOrderId) {
		
		return placeOrderPendingTransctionDao.getTransactionDetails(ratePlaceOrderId);
	}
}
