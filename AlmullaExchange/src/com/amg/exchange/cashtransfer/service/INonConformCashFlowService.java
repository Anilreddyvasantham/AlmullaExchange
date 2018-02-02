package com.amg.exchange.cashtransfer.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.cashtransfer.model.NonConfirmCashFlow;
import com.amg.exchange.cashtransfer.model.NonConfirmCashFlowDetails;

public interface INonConformCashFlowService {
	
	public List<NonConfirmCashFlow> getAllNonConformCashFlowList();

	public List<NonConfirmCashFlowDetails> getdetails(BigDecimal cashHeaderId);
}
