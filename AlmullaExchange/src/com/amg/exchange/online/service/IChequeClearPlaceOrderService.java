package com.amg.exchange.online.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.amg.exchange.online.bean.RatePlaceOrderDTO;

public interface IChequeClearPlaceOrderService {

	public List<RatePlaceOrderDTO> toFetchAllRecordsFromDb(BigDecimal customerId);

	public void saveOrUpdatePlaceOrder(List<BigDecimal> lstPkUpdates,String userName);

	public String getBankNamebasedonBankCode(String bankCode);
	
	public HashMap<String, String> placeOrderRemitTranxProcedure(HashMap<String, Object> inputValues) throws Exception;
	
	public String upadteCheckClearence(HashMap<String, Object> inputValues);

	

}
