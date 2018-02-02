package com.amg.exchange.remittance.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.model.StandingInstruction;
import com.amg.exchange.remittance.bean.StandingInstructionList;
import com.amg.exchange.remittance.dao.IStandingInstructionDao;
import com.amg.exchange.remittance.service.IStandingInstructionService;
import com.amg.exchange.treasury.model.TreasuryStandardInstruction;

@SuppressWarnings("serial")
@Service("standingInstructionService")
public class StandingInstructionServiceImpl<T> implements IStandingInstructionService<T> {

	@Autowired
	IStandingInstructionDao<T> standingInstrnDao;
	
	
	@Override
	@Transactional
	public void saveStandingInstruction(StandingInstruction standingInstrn) {
		
		standingInstrnDao.saveStandingInstruction(standingInstrn);
	}


	@Override
	@Transactional
	public List<StandingInstruction> standingInstrnDetailsByID(BigDecimal standingInstrnId) {
		// TODO Auto-generated method stub
		return standingInstrnDao.standingInstrnDetailsByID(standingInstrnId);
	}


	@Override
	@Transactional
	public List<StandingInstruction> standingInstrnAllDetailsbyCustomer(BigDecimal customerId) {
		// TODO Auto-generated method stub
		return standingInstrnDao.standingInstrnAllDetailsbyCustomer(customerId);
	}


	@Override
	@Transactional
	public List<TreasuryStandardInstruction> getAllRecordsFrom(BigDecimal treasuryDealHeaderId, BigDecimal dealNo) {
		return standingInstrnDao.getAllRecordsFrom(treasuryDealHeaderId,dealNo);
		
	}

}
