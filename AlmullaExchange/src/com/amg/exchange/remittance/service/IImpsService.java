package com.amg.exchange.remittance.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.remittance.model.Imps;

public interface IImpsService {

	public  void saveOrUpdate(Imps impsObj);
	public List<Imps> getImpsRecordsBasedOnBankIds( BigDecimal corrspBankId,BigDecimal beneBankId);
	public List<Imps> getAllRecordsFromDB();
	public void delete(BigDecimal impsPk);
	public void activateRecord(BigDecimal impsPk,String userName);
	public List<Imps> getAllUnApprovedRecords();
	public String approveRecord(BigDecimal documentPk, String userName);
	
	
}
