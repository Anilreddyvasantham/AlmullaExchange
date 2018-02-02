package com.amg.exchange.remittance.dao;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.model.StandingInstruction;
import com.amg.exchange.remittance.bean.StandingInstructionList;
import com.amg.exchange.treasury.model.TreasuryStandardInstruction;

public interface IStandingInstructionDao<T> {
	
	public void saveStandingInstruction(StandingInstruction standingInstrn);
	
	public List<StandingInstruction> standingInstrnDetailsByID(BigDecimal standingInstrnId);
	
	public List<StandingInstruction> standingInstrnAllDetailsbyCustomer(BigDecimal customerId);

	public List<TreasuryStandardInstruction> getAllRecordsFrom(BigDecimal treasuryDealHeaderId,
			BigDecimal dealNo);

}
