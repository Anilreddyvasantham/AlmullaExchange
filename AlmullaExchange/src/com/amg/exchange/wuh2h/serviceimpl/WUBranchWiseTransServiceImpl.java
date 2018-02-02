package com.amg.exchange.wuh2h.serviceimpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.wuh2h.model.WUH2HTransactionView;
import com.amg.exchange.wuh2h.service.IWUBranchWiseTransService;
import com.amg.exchange.wuh2h.settlement.dao.IWUBranchWiseTransDAO;

@Service("wUBranchWiseTransServiceImpl")
public class WUBranchWiseTransServiceImpl implements IWUBranchWiseTransService {
	
	@Autowired
	IWUBranchWiseTransDAO wUBranchWiseTransDAO;
	
	@Override
	@Transactional
	public List<WUH2HTransactionView> getBranchwiseTransactionCountList(BigDecimal sendDocumentCode,BigDecimal recvDocumentCode,BigDecimal companyId,BigDecimal applicationCountryId,Date transactionFromDate,Date transactionToDate) {
		return wUBranchWiseTransDAO.getBranchwiseTransactionCountList(sendDocumentCode, recvDocumentCode, companyId, applicationCountryId, transactionFromDate, transactionToDate);
	}
	
	@Override
	@Transactional
	public HashMap<String,Object> branchWiseSendRecieveList(Date transactionFromDate,Date transactionToDate){
		return wUBranchWiseTransDAO.branchWiseSendRecieveList(transactionFromDate, transactionToDate);
	}

}
