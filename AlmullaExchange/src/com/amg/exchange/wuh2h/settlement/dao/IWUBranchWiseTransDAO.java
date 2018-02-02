package com.amg.exchange.wuh2h.settlement.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.amg.exchange.wuh2h.model.WUH2HTransactionView;

public interface IWUBranchWiseTransDAO {
	
	public List<WUH2HTransactionView> getBranchwiseTransactionCountList(BigDecimal sendDocumentCode,BigDecimal recvDocumentCode,BigDecimal companyId,BigDecimal applicationCountryId,Date transactionFromDate,Date transactionToDate);
	public HashMap<String,Object> branchWiseSendRecieveList(Date transactionFromDate,Date transactionToDate);

}
