package com.amg.exchange.common.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.common.dao.IBankBannedWordsMaintenanceDao;
import com.amg.exchange.common.model.BankBannedWordsMaintenance;
import com.amg.exchange.common.service.IBankBannedWordsMaintenanceService;
@Service
public class BankBannedWordsMaintenanceServiceImpl implements
		IBankBannedWordsMaintenanceService {
	@Autowired 
	IBankBannedWordsMaintenanceDao ibankBannedWordsDao;

	@Override
	@Transactional
	public void saveOrUpdate(
			BankBannedWordsMaintenance bankBannedWordMaintenanceObj) {
		 
		ibankBannedWordsDao.saveOrUpdate(bankBannedWordMaintenanceObj);
	}

	@Override
	@Transactional
	public List<BankBannedWordsMaintenance> getSpecificReords(BigDecimal bankId, BigDecimal countryId,String selectionMode) {
		 
		return ibankBannedWordsDao.getSpecificReords(bankId,countryId,selectionMode);
	}

	 

	@Override
	@Transactional
	public List<BankBannedWordsMaintenance> getAllBannedWordsList(
			String selectionMode) {
		 
		return ibankBannedWordsDao.getAllBannedWordsList(selectionMode);
	}

	@Override
	@Transactional
	public List<BankBannedWordsMaintenance> duplicateWordCheck(String bannedName) {
		 
		return ibankBannedWordsDao.duplicateWordCheck(bannedName);
	}

	@Override
	@Transactional
	public void deleteRecord(BigDecimal bankBannedId, String userName) {
		ibankBannedWordsDao.deleteRecord(bankBannedId,userName);
		
	}

	@Override
	@Transactional
	public void activateWord(BigDecimal bankBannedId, String userName) {
		ibankBannedWordsDao.activateWord(bankBannedId,userName);
		
	}

	
}
