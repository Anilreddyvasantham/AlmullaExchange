package com.amg.exchange.common.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.common.dao.IBankLengthDao;
import com.amg.exchange.common.service.IBankLengthService;
import com.amg.exchange.treasury.model.BankAccountLength;

@Service("bankLengthService")
public class BankLengthServiceImpl implements IBankLengthService {
	
	@Autowired
	IBankLengthDao bankLengthDao;

	@Override
	@Transactional
	public void saveBankLengthRecord(BankAccountLength bankLength) {
		bankLengthDao.saveBankLengthRecord(bankLength);
	}

	@Override
	@Transactional
	public List<BankAccountLength> findBankId(BigDecimal bankId) {
		// TODO Auto-generated method stub
		return bankLengthDao.findBankId(bankId);
	}

	@Override
	@Transactional
	public List<BankAccountLength> checkBanklength(BigDecimal bankId,BigDecimal bankLength) {
		return bankLengthDao.checkBankLength(bankId, bankLength);
	}

	@Override
	@Transactional
	public void deleteBankLengthRecord(BigDecimal bankLengthId, String username,Boolean delete) {
		// TODO Auto-generated method stub
		bankLengthDao.deleteBankLengthRecord(bankLengthId, username,delete);
	}
	
	@Override
	@Transactional
	public List<BankAccountLength> findAllLengthByBankId(BigDecimal bankId) {
		// TODO Auto-generated method stub
		return bankLengthDao.findAllLengthByBankId(bankId);
	}

	/*koti start activate and deactivate service bank length ############ 17062016*/
	@Override
	@Transactional
	public void DeActiveRecordToPendingForApprovalOfBankLength(BigDecimal bankLengthId, String userName) {
		bankLengthDao.DeActiveRecordToPendingForApprovalOfBankLength(bankLengthId, userName);
	}

	@Override
	@Transactional
	public void upDateActiveRecordtoDeActive(BigDecimal bankLengthId,String remarks, String userName) {
		bankLengthDao.upDateActiveRecordtoDeActive(bankLengthId, remarks, userName);
	}

	@Override
	@Transactional
	public void DeActiveRecordToPendingForApprovalOfBankMaster(BigDecimal bankIdInternal, String userName) {
		bankLengthDao.DeActiveRecordToPendingForApprovalOfBankMaster(bankIdInternal, userName);
	}

	@Override
	@Transactional
	public void upDateActiveRecordtoDeActiveBankMaster(BigDecimal bankIdInternal, String userName) {
		bankLengthDao.upDateActiveRecordtoDeActiveBankMaster(bankIdInternal, userName);
	}

	@Override
	@Transactional
	public List<BankAccountLength> findBankIdApprovalBankLength(BigDecimal bankIdInternal) {
		return bankLengthDao.findBankIdApprovalBankLength(bankIdInternal);
	}
	/*koti End activate and deactivate service bank length ############ 17062016*/

	@Override
	@Transactional
	public void deleteRecordBankAccountLength(BigDecimal bankLengthId) {
		bankLengthDao.deleteRecordBankAccountLength(bankLengthId);
	}

}
