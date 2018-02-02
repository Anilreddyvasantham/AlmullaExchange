package com.amg.exchange.online.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.amg.exchange.online.model.RatePlaceOrder;
import com.amg.exchange.remittance.model.PaymentMode;

public interface IPlaceOrederBranchSupportService {

	public List<RatePlaceOrder> toFetchAllRecordsFromDb(BigDecimal countryBranchCode);

	public String toFetchMobileNumBasedOnCustomerId(BigDecimal customerId);

	public void saveAppointmentTime(BigDecimal placeOrderPk,Date appointmentTime,BigDecimal sourceId,String paymentId, String userName);

	public HashMap<String,String> createRemitAppProcedure(HashMap<String,String> inputValues)throws Exception;

	public String saveOrUpdatePlaceOrderAddlData(HashMap<String, Object> inputValues)throws Exception;

	public List<PaymentMode> fetchPaymodeDescForOnlineCustomer(String isActive);
}
