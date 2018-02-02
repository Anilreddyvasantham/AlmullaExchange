package com.amg.exchange.treasury.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.treasury.model.BankAccount;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankMaster;

public interface IBankAccountService<T> {

	public void saveBankAccount(BankAccount bankaccount);
	public List<BankBranch> getBankBranchList(BigDecimal bankId);
	public List<BankMaster> getCurrencyOfBank(BigDecimal countryId,BigDecimal bankId);
	public List<BankAccount> getDuplicateAccountNo(BigDecimal bankId,String bankAccountNo);
	public void approveBankAccount(BigDecimal bankAccPk,String userName);
	
	public void procErrorToUnApprove(BigDecimal bankAccountDetId,BigDecimal fetchAccHeaderId);

}
