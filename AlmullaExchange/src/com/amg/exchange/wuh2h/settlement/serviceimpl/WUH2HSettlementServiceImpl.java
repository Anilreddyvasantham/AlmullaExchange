package com.amg.exchange.wuh2h.settlement.serviceimpl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.wuh2h.dao.IWUH2HDao;
import com.amg.exchange.wuh2h.settlement.dao.IWUH2HSettlementDao;
import com.amg.exchange.wuh2h.settlement.model.WUTxnSummaryView;
import com.amg.exchange.wuh2h.settlement.service.IWUH2HSettlementService;

@Service
@Transactional
public class WUH2HSettlementServiceImpl implements IWUH2HSettlementService, Serializable {
	
private static final long serialVersionUID = 1L;
	
	@Autowired
	IWUH2HSettlementDao wuh2hSettlementDao;
	
	
	
	@Override
	@Transactional
	public List<WUTxnSummaryView> getWUTransactionSummary(Date txndate){
		return wuh2hSettlementDao.getWUTransactionSummary(txndate);
	}
}
