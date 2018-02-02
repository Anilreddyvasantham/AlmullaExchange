package com.amg.exchange.treasury.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.amg.exchange.common.model.BankAccountType;
import com.amg.exchange.common.model.BankAccountTypeDesc;
import com.amg.exchange.jvprocess.model.ViewGeneralValidationGlNo;
import com.amg.exchange.treasury.bean.BankAccountDataTable;
import com.amg.exchange.treasury.model.AccountBalance;
import com.amg.exchange.treasury.model.BankAccount;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.util.AMGException;

public interface IBankAccountDetailsService<T> {
	public  void saveBankAccountDetails(HashMap<String, Object> saveMapInfo)throws Exception;
	
	public void saveBankAccountDetails(BankAccountDetails bankaccdetails);
	public List<BankAccountDetails> getBankAccountDetails(BigDecimal bankId,String bankAcctNo);
	public void saveBankAccountDetailsId(AccountBalance accountBalance);
	public List<BankMaster> getCurrencyOfBank(BigDecimal countryId,BigDecimal bankId);
	public List<BankAccount> getBankAccountNoFromBankAccount(BigDecimal countryId, BigDecimal bankId);
	public List<BankAccountTypeDesc> getAccountTypeList(BigDecimal languageId);
	public List<BankAccountDetails> getBankAccountDetails(BigDecimal countryId,BigDecimal bankId,String accountNo);
	
	public List<AccountBalance> getAccountBalance(String accountNo,BigDecimal bankActDtlsID);
	
	//get records for bank account details view 
	public List<BankAccountDetails> getDataForView(BigDecimal countryId,BigDecimal bankId);
	
	//get account type name per type id
	public String getAccountTypeName(BigDecimal accountTypeId);
	
	public String approveBankAccDtls(HashMap<String, Object> saveMapInfo)throws Exception;
	
	public List<ViewGeneralValidationGlNo> getAccountBalanceGLNo(BigDecimal companyCode,String glno);
	
	public List<ViewGeneralValidationGlNo> getAccountBalanceGLNoWithCurrency(String currencyCode,String glno);
	
	public HashMap<String, String> callPopulateBankAccountDeatils(HashMap<String, String> inputValues) throws AMGException;
	
	public HashMap<String, String> getAccountBalanceGLNoWithCurrencyWithProc(BigDecimal currencyId,String glno) throws AMGException;
}
