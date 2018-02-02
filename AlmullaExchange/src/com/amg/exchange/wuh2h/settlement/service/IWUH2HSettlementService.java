package com.amg.exchange.wuh2h.settlement.service;

import java.util.Date;
import java.util.List;

import com.amg.exchange.wuh2h.settlement.model.WUTxnSummaryView;

public interface IWUH2HSettlementService {
	
	public List<WUTxnSummaryView> getWUTransactionSummary(Date txndate);
}
