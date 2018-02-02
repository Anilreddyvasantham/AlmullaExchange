package com.amg.exchange.cashtransfer.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.cashtransfer.dao.INonConformCashFlowDao;
import com.amg.exchange.cashtransfer.model.NonConfirmCashFlow;
import com.amg.exchange.cashtransfer.model.NonConfirmCashFlowDetails;
import com.amg.exchange.cashtransfer.service.INonConformCashFlowService;

@Service
public class NonConformCashFlowLServiceImpl implements INonConformCashFlowService{
	
	
	INonConformCashFlowDao cashTransferLToLDao;
	
	

	public INonConformCashFlowDao getCashTransferLToLDao() {
		return cashTransferLToLDao;
	}


	@Autowired
	public void setCashTransferLToLDao(INonConformCashFlowDao cashTransferLToLDao) {
		this.cashTransferLToLDao = cashTransferLToLDao;
	}



	@Override
	@Transactional
	public List<NonConfirmCashFlow> getAllNonConformCashFlowList() {
		
		return getCashTransferLToLDao().getAllNonConformCashFlowList();
	}


	@Override
	@Transactional
	public List<NonConfirmCashFlowDetails> getdetails(BigDecimal cashHeaderId) {
		return getCashTransferLToLDao().getdetails(cashHeaderId);
	}

	

	
}
