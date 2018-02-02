package com.amg.exchange.online.serviceimpl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.online.bean.RatePlaceOrderDTO;
import com.amg.exchange.online.dao.IChequeClearPlaceOrderDao;
import com.amg.exchange.online.service.IChequeClearPlaceOrderService;
@Service("chequeClearPlaceOrderServiceImpl")
@Transactional
public class ChequeClearPlaceOrderServiceImpl implements IChequeClearPlaceOrderService {
	
	@Autowired
	IChequeClearPlaceOrderDao chequeClearPlaceOrderDao;

	@Override
	public List<RatePlaceOrderDTO> toFetchAllRecordsFromDb(BigDecimal customerId) {
		return chequeClearPlaceOrderDao.toFetchAllRecordsFromDb(customerId);
	}

	@Override
	public void saveOrUpdatePlaceOrder(List<BigDecimal> lstPkUpdates,String userName) {
		chequeClearPlaceOrderDao.saveOrUpdatePlaceOrder(lstPkUpdates, userName);
	}

	@Override
	public String getBankNamebasedonBankCode(String bankCode) {
		return chequeClearPlaceOrderDao.getBankNamebasedonBankCode(bankCode);
	}

	@Override
	public HashMap<String, String> placeOrderRemitTranxProcedure(HashMap<String, Object> inputValues) throws Exception {
		return chequeClearPlaceOrderDao.placeOrderRemitTranxProcedure(inputValues);
	}

	@Override
	public String upadteCheckClearence(HashMap<String, Object> inputValues) {
		return chequeClearPlaceOrderDao.upadteCheckClearence(inputValues);
		
	}

}
