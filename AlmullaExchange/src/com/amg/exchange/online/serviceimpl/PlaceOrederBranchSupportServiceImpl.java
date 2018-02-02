package com.amg.exchange.online.serviceimpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.online.dao.IPlaceOrederBranchSupportDao;
import com.amg.exchange.online.model.RatePlaceOrder;
import com.amg.exchange.online.service.IPlaceOrederBranchSupportService;
import com.amg.exchange.remittance.model.PaymentMode;
@Service("placeOrederBranchSupportServiceImpl")
@Transactional
public class PlaceOrederBranchSupportServiceImpl implements IPlaceOrederBranchSupportService{

	@Autowired
	IPlaceOrederBranchSupportDao placeOrederBranchSupportDao;

	@Override
	public List<RatePlaceOrder> toFetchAllRecordsFromDb(BigDecimal countryBranchCode) {
		return placeOrederBranchSupportDao.toFetchAllRecordsFromDb(countryBranchCode);
	}

	@Override
	public String toFetchMobileNumBasedOnCustomerId(BigDecimal customerId) {
		return placeOrederBranchSupportDao.toFetchMobileNumBasedOnCustomerId(customerId);
	}

	@Override
	public void saveAppointmentTime(BigDecimal placeOrderPk,Date appointmentTime,BigDecimal sourceId,String paymentId, String userName) {
		placeOrederBranchSupportDao.saveAppointmentTime(placeOrderPk, appointmentTime,sourceId,paymentId, userName);
	}

	@Override
	public HashMap<String, String> createRemitAppProcedure(
			HashMap<String, String> inputValues) throws Exception {
		
		return placeOrederBranchSupportDao.createRemitAppProcedure(inputValues);
	}

	@Override
	public String saveOrUpdatePlaceOrderAddlData(
			HashMap<String, Object> inputValues) throws Exception {
		return placeOrederBranchSupportDao.saveOrUpdatePlaceOrderAddlData(inputValues);
		
	}

	@Override
	public List<PaymentMode> fetchPaymodeDescForOnlineCustomer(String isActive) {
		return placeOrederBranchSupportDao.fetchPaymodeDescForOnlineCustomer(isActive);
	}
}
