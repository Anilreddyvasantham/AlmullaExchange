package com.amg.exchange.wuh2h.settlement.dao;

import java.util.Date;
import java.util.List;

import com.amg.exchange.wuh2h.settlement.model.WUTxnSummaryView;


public interface IWUH2HSettlementDao {
	
	
	public List<WUTxnSummaryView> getWUTransactionSummary(Date txndate);
}
