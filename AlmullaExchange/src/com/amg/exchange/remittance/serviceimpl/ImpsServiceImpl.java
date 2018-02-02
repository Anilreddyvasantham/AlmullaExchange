package com.amg.exchange.remittance.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.codehaus.groovy.transform.trait.Traits.TraitBridge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.remittance.dao.IImpsDao;
import com.amg.exchange.remittance.model.Imps;
import com.amg.exchange.remittance.service.IImpsService;
@Service
public class ImpsServiceImpl implements IImpsService {

	@Autowired 
	IImpsDao iimpsDao;
	
	
	
	@Override
	@Transactional
	public void saveOrUpdate(Imps impsObj) {
		iimpsDao.saveOrUpdate(impsObj);
		
		
	}

	@Override
	@Transactional
	public List<Imps> getImpsRecordsBasedOnBankIds(BigDecimal corrspBankId,
			 BigDecimal beneBankId) {
		 
		return iimpsDao.getImpsRecordsBasedOnBankIds(corrspBankId,beneBankId);
	}

	@Override
	@Transactional
	public List<Imps> getAllRecordsFromDB() {
		 
		return iimpsDao.getAllRecordsFromDB();
	}

	@Override
	@Transactional
	public void delete(BigDecimal impsPk) {
		iimpsDao.delete(impsPk);
		
	}

	@Override
	@Transactional
	public void activateRecord(BigDecimal impsPk, String userName) {
		iimpsDao.activateRecord(impsPk,userName);
		
	}

	@Override
	@Transactional
	public List<Imps> getAllUnApprovedRecords() {
 
		return iimpsDao.getAllUnApprovedRecords();
	}

	@Override
	@Transactional
	public String approveRecord(BigDecimal documentPk, String userName) {
		 
		return iimpsDao.approveRecord(documentPk,userName);
	}

	
}
