package com.amg.exchange.treasury.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.common.model.BankAccountTypeDesc;
import com.amg.exchange.jvprocess.model.ViewGeneralValidationGlNo;
import com.amg.exchange.treasury.dao.IBankAccountDetailsDao;
import com.amg.exchange.treasury.model.AccountBalance;
import com.amg.exchange.treasury.model.BankAccount;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.service.IBankAccountDetailsService;
import com.amg.exchange.util.AMGException;

@SuppressWarnings("serial")
@Service("bankAccountDetailsServiceImpl")
public class BankAccountDetailsServiceImpl<T> implements IBankAccountDetailsService<T>, Serializable{

	@Autowired
	IBankAccountDetailsDao<T> bankAccountDetailsDao;
	
	
	public IBankAccountDetailsDao<T> getBankAccountDetailsDao() {
		return bankAccountDetailsDao;
	}
	public void setBankAccountDetailsDao(IBankAccountDetailsDao<T> bankAccountDetailsDao) {
		this.bankAccountDetailsDao = bankAccountDetailsDao;
	}

	@Transactional
	@Override
	public void saveBankAccountDetails(BankAccountDetails bankaccdetails) {
	getBankAccountDetailsDao().saveBankAccountDetails(bankaccdetails);
	}
	
	@Transactional
	@Override
	public List<BankAccountDetails> getBankAccountDetails(BigDecimal bankId,String bankAcctNo) {
		return getBankAccountDetailsDao().getBankAccountDetails(bankId,bankAcctNo);
	}
	@Transactional
	@Override
	public void saveBankAccountDetailsId(AccountBalance accountBalance) {
		 getBankAccountDetailsDao().saveBankAccountDetailsId(accountBalance);
	}

	@Transactional
	@Override
	public List<BankMaster> getCurrencyOfBank(BigDecimal countryId,BigDecimal bankId) {
		return getBankAccountDetailsDao().getCurrencyOfBank(countryId,bankId);
	}
	@Transactional
	@Override
	public List<BankAccount> getBankAccountNoFromBankAccount(BigDecimal countryId, BigDecimal bankId) {
		return getBankAccountDetailsDao().getBankAccountNoFromBankAccount(countryId,bankId);
	}
	
	@Transactional
	@Override
	public List<BankAccountTypeDesc> getAccountTypeList(BigDecimal languageId) {
		return getBankAccountDetailsDao().getAccountTypeList(languageId);
	}

	@Override
	@Transactional
	public List<BankAccountDetails> getBankAccountDetails(BigDecimal countryId,	BigDecimal bankId, String accountNo) {
		return getBankAccountDetailsDao().getBankAccountDetails(countryId,bankId,accountNo);
	}

	@Override
	@Transactional
	public List<AccountBalance> getAccountBalance(String accountNo,	BigDecimal bankActDtlsID) {
		return getBankAccountDetailsDao().getAccountBalance(accountNo, bankActDtlsID);
	}
	
	
	@Override
	@Transactional
	public List<BankAccountDetails> getDataForView(BigDecimal countryId,BigDecimal bankId) {
		return getBankAccountDetailsDao().getDataForView(countryId, bankId);
	}
	
	@Override
	@Transactional
	public String getAccountTypeName(BigDecimal accountTypeId) {
		return getBankAccountDetailsDao().getAccountTypeName(accountTypeId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String approveBankAccDtls(HashMap<String, Object> saveMapInfo)throws Exception {
		return getBankAccountDetailsDao().approveBankAccDtls(saveMapInfo);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveBankAccountDetails(HashMap<String, Object> saveMapInfo)	throws Exception {
		getBankAccountDetailsDao().saveBankAccountDetails(saveMapInfo);//(rollbackFor = Exception.class)
	}

	@Override
	@Transactional
	public List<ViewGeneralValidationGlNo> getAccountBalanceGLNo(BigDecimal companyCode, String glno) {
		return getBankAccountDetailsDao().getAccountBalanceGLNo(companyCode, glno);
	}
	
	@Override
	@Transactional
	public List<ViewGeneralValidationGlNo> getAccountBalanceGLNoWithCurrency(String currencyCode, String glno) {
		return getBankAccountDetailsDao().getAccountBalanceGLNoWithCurrency(currencyCode, glno);
	}
	
	@Override
	@Transactional
	public HashMap<String, String> callPopulateBankAccountDeatils(HashMap<String, String> inputValues) throws AMGException {
		return getBankAccountDetailsDao().callPopulateBankAccountDeatils(inputValues);
	}

	@Override
	@Transactional(rollbackFor = AMGException.class)
	public HashMap<String, String> getAccountBalanceGLNoWithCurrencyWithProc(BigDecimal currencyId, String glno) throws AMGException{
		return getBankAccountDetailsDao().getAccountBalanceGLNoWithCurrencyWithProc(currencyId, glno);
	}

}
