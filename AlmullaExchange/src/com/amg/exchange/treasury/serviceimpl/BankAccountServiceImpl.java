package com.amg.exchange.treasury.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.treasury.dao.IBankAccountDao;
import com.amg.exchange.treasury.model.BankAccount;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.service.IBankAccountService;
@SuppressWarnings("serial")
@Service("bankAccountServiceImpl")
public class BankAccountServiceImpl<T> implements IBankAccountService<T>, Serializable {

	@Autowired
	IBankAccountDao<T> bankAccountDao;
	
	
	public IBankAccountDao<T> getBankAccountDao() {
		return bankAccountDao;
	}

	public void setBankAccountDao(IBankAccountDao<T> bankAccountDao) {
		this.bankAccountDao = bankAccountDao;
	}

	@Transactional
	@Override
	public void saveBankAccount(BankAccount bankaccount) {
		getBankAccountDao().saveBankAccount(bankaccount);
		
	}
	
	@Transactional
	@Override
	public List<BankBranch> getBankBranchList(BigDecimal bankId) {
		return getBankAccountDao().getBankBranchList(bankId);
	}
	@Transactional
	@Override
	public List<BankMaster> getCurrencyOfBank(BigDecimal countryId,BigDecimal bankId) {
		// TODO Auto-generated method stub
		return getBankAccountDao().getCurrencyOfBank(countryId,bankId);
	}
	@Transactional
	@Override
	public List<BankAccount> getDuplicateAccountNo(BigDecimal bankId, String bankAccountNo) {
		return getBankAccountDao().getDuplicateAccountNo(bankId,bankAccountNo);
	}
	
	@Transactional
	@Override
	public void approveBankAccount(BigDecimal bankAccPk,String userName) {
		getBankAccountDao().approveBankAccount(bankAccPk,userName);
	}

	@Override
	@Transactional
	public void procErrorToUnApprove(BigDecimal bankAccountDetId,
			BigDecimal fetchAccHeaderId) {
		getBankAccountDao().procErrorToUnApprove(bankAccountDetId, fetchAccHeaderId);
	}

}
