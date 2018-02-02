package com.amg.exchange.treasury.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.treasury.dao.IBankMasterDao;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.service.IBankMasterService;
import com.amg.exchange.util.AMGException;

@SuppressWarnings("serial")
@Service("bankMasterContactDetailsServiceImpl")
public class BankmasterContactDetailsServiceImpl<T> implements IBankMasterService<T>, Serializable {
	
	@Autowired
	IBankMasterDao<T> bankMasterDao;
	public IBankMasterDao<T> getBankMasterDao() {
		return bankMasterDao;
	}
	public void setBankMasterDao(IBankMasterDao<T> bankMasterDao) {
		this.bankMasterDao = bankMasterDao;
	}

	@Transactional
	@Override
	public void saveBankMasterInfo(BankMaster bankMasterinfo) {
		getBankMasterDao().saveBankMasterInfo(bankMasterinfo);
	}
	
	/**
	 * Responsible to fetch all Bank Master data from database depending upon given bank code
	 */
	@Transactional
	@Override
	public List<BankMaster> getBankMasterInfo(String bankCode) {
		return getBankMasterDao().getBankMasterInfo(bankCode);
	}
	
	@Transactional
	@Override
	public List<BankMaster> getBankMasterInfoForApproval(String bankCode) {
		return getBankMasterDao().getBankMasterInfoForApproval(bankCode);
	}
	
	@Transactional
	@Override
	public List<BankMaster> getAllBankMasterInfo(String bankCode) {
		return getBankMasterDao().getAllBankMasterInfo(bankCode);
	}
	
	@Transactional
	@Override
	public List<BankMaster> getBankMasterInfoById(BigDecimal bankId){
		return getBankMasterDao().getBankMasterInfoById(bankId);
	}
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveAll(HashMap<String, Object> saveMap)
			throws Exception {
		
		getBankMasterDao().saveAll(saveMap);
	}
	@Override
	@Transactional
	public HashMap<String, String> callPopulateBankMaster(
			HashMap<String, String> inputValues) throws AMGException {
 
		return getBankMasterDao().callPopulateBankMaster(inputValues);
		
	}
	@Override
	@Transactional
	public void procErrorToUnApprove(BigDecimal bankIdInternal,
			List<BigDecimal> contactList, List<BigDecimal> lengthLst) {
		getBankMasterDao().procErrorToUnApprove(bankIdInternal, contactList, lengthLst);
	}
	
	@Override
	@Transactional
	public List<BankMaster> fetchBankServiceProvider() {
		// TODO Auto-generated method stub
		return getBankMasterDao().fetchBankServiceProvider();
	}
	
} 
