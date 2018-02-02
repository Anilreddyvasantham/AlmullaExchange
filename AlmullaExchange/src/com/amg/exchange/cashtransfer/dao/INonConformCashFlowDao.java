package com.amg.exchange.cashtransfer.dao;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.cashtransfer.model.NonConfirmCashFlow;
import com.amg.exchange.cashtransfer.model.NonConfirmCashFlowDetails;



public interface INonConformCashFlowDao {
	
	public List<NonConfirmCashFlow> getAllNonConformCashFlowList();

	public List<NonConfirmCashFlowDetails> getdetails(BigDecimal cashHeaderId);
}
