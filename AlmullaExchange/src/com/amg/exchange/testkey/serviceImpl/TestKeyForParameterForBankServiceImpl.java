package com.amg.exchange.testkey.serviceImpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.common.model.BankAccountTypeDesc;
import com.amg.exchange.testkey.dao.ITestKeyForParameterForBankDao;
import com.amg.exchange.testkey.model.TestKeyMaster;
import com.amg.exchange.testkey.model.TestKeyValues;
import com.amg.exchange.testkey.model.ViewTestKeyParameter;
import com.amg.exchange.testkey.service.ITestKeyForParameterForBankService;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.CurrencyMaster;

@Service("testKeyForParameterForBankService")
public class TestKeyForParameterForBankServiceImpl implements ITestKeyForParameterForBankService {

	@Autowired
	ITestKeyForParameterForBankDao testKeyForParameterForBankDao;

	@Override
	@Transactional
	public List<BankApplicability> toFetchCorpondingAllBank(BigDecimal countryId) {
		return testKeyForParameterForBankDao.toFetchCorpondingAllBank(countryId);
	}

	@Override
	@Transactional
	public List<BankAccountDetails> toFetchAccountNumberBasedOnBank(BigDecimal bankId) {
		return testKeyForParameterForBankDao.toFetchAccountNumberBasedOnBank(bankId);
	}

	@Override
	@Transactional
	public List<BankAccountDetails> toFetchCurrencyBasedOnAccountNumber(BigDecimal accountNo) {
		return testKeyForParameterForBankDao.toFetchCurrencyBasedOnAccountNumber(accountNo);
	}

	@Override
	@Transactional
	public List<BankBranch> toFetchBranchBasedOnBank(BigDecimal bankId) {
		return testKeyForParameterForBankDao.toFetchBranchBasedOnBank(bankId);
	}

	@Override
	@Transactional
	public List<BankAccountTypeDesc> tofetchBankAccountTypeDescBasedOnAccountNo(BigDecimal bankAccoutTypeId, BigDecimal languageId) {
		return testKeyForParameterForBankDao.tofetchBankAccountTypeDescBasedOnAccountNo(bankAccoutTypeId, languageId);
	}

	@Override
	@Transactional
	public List<ViewTestKeyParameter> tofetchAllPrimaryParameters() {
		return testKeyForParameterForBankDao.tofetchAllPrimaryParameters();
	}

	@Override
	@Transactional
	public List<TestKeyMaster> fetchtheRecordFromTestKeyMaster(BigDecimal countryId, BigDecimal bankId, String accountNo, BigDecimal branchCode, String sendReceiveIndicator) {
		return testKeyForParameterForBankDao.fetchtheRecordFromTestKeyMaster(countryId, bankId, accountNo, branchCode, sendReceiveIndicator);
	}

	@Override
	@Transactional
	public List<TestKeyMaster> toViewForAllrecordsTofetchDb() {
		return testKeyForParameterForBankDao.toViewForAllrecordsTofetchDb();
	}

	@Override
	@Transactional
	public List<BankAccountTypeDesc> fetchAccountDescTypeBasedonLanguageId(BigDecimal languageId) {
		return testKeyForParameterForBankDao.fetchAccountDescTypeBasedonLanguageId(languageId);
	}

	@Override
	@Transactional
	public Boolean saveTestKeyValues(List<TestKeyValues> lstTestKeyValues) {
		return testKeyForParameterForBankDao.saveTestKeyValues(lstTestKeyValues);
	}

	@Override
	@Transactional
	public String tofetchAllPrimaryParameters(String primaryTestKey) {
		return testKeyForParameterForBankDao.tofetchAllPrimaryParameters(primaryTestKey);
	}

	@Override
	@Transactional
	public String toFetchParameterTestKeyName(String primaryTestKey) {
		return testKeyForParameterForBankDao.toFetchParameterTestKeyName(primaryTestKey);
	}

	@Override
	@Transactional
	public void saveTestKeyMaster(TestKeyMaster testKeyMaster) {
		testKeyForParameterForBankDao.saveTestKeyMaster(testKeyMaster);
	}


	@Override
	@Transactional
	public List<TestKeyValues> viewAllTestKeyValues() {
		return testKeyForParameterForBankDao.viewAllTestKeyValues();
	}


	@Override
	@Transactional
	public String toFetchBankBranch(BigDecimal bankBranchId) {
		return testKeyForParameterForBankDao.toFetchBankBranch(bankBranchId);
	}

	@Override
	@Transactional
	public BigDecimal tofetchAccountId(String bankAccountNumber, BigDecimal bankId) {
		return testKeyForParameterForBankDao.tofetchAccountId(bankAccountNumber, bankId);
	}

	@Override
	@Transactional
	public void deleteRecordTestKeyMaster(BigDecimal testKeyParameterPk) {
		testKeyForParameterForBankDao.deleteRecordTestKeyMaster(testKeyParameterPk);
	}

	@Override
	@Transactional
	public void deActivateRecord(BigDecimal testKeyParameterPk, String userName) {
		testKeyForParameterForBankDao.deActivateRecord(testKeyParameterPk, userName);
	}

	@Override
	@Transactional
	public List<CurrencyMaster> tofetchCurrencyId(String currencyName) {
		return testKeyForParameterForBankDao.tofetchCurrencyId(currencyName);
	}

	@Override
	@Transactional
	public List<BankAccountDetails> tofetchAccountIdAccountTypeId(String bankAccountNumber, BigDecimal bankId) {
		return testKeyForParameterForBankDao.tofetchAccountIdAccountTypeId(bankAccountNumber, bankId);
	}

	@Override
	@Transactional
	public List<TestKeyMaster> toApprovalForAllrecordsTofetchDb() {
		return testKeyForParameterForBankDao.toApprovalForAllrecordsTofetchDb();
	}

	@Override
	@Transactional
	public String checkTestKeyMasterApproveMultiUser(BigDecimal testKeyParameterPk, String userName) {
		return testKeyForParameterForBankDao.checkTestKeyMasterApproveMultiUser(testKeyParameterPk,userName);
	}

	@Override
	@Transactional
	public List<TestKeyValues> fetchtheRecordFromTestKeyValue(BigDecimal appcountryId, BigDecimal bankId, String accountNo, BigDecimal branchCode, String sendReceiveIndicator) {
		return testKeyForParameterForBankDao.fetchtheRecordFromTestKeyValue(appcountryId, bankId, accountNo, branchCode, sendReceiveIndicator);
	}

	@Override
	@Transactional
	public void deActivateRecordTestKeyValues(BigDecimal testKeyvaluePk,String userName) {
		testKeyForParameterForBankDao.deActivateRecordTestKeyValues(testKeyvaluePk,userName);
	}

	@Override
	@Transactional
	public void deleteRecordTestKeyValues(BigDecimal testKeyvaluePk) {
		testKeyForParameterForBankDao.deleteRecordTestKeyValues(testKeyvaluePk);
	}

	@Override
	@Transactional
	public void activatedTestKeyValues(BigDecimal testKeyvaluePk, String userName, String remarks) {
		testKeyForParameterForBankDao.activatedTestKeyValues(testKeyvaluePk,userName,remarks);
	}

	@Override
	@Transactional
	public List<TestKeyMaster> fetchRecordFromTestKeyMasterForPrimaryKey(BigDecimal countryId, BigDecimal bankId, String accountNo,BigDecimal branchCode, String sendReceiveIndicator) {
		return testKeyForParameterForBankDao.fetchRecordFromTestKeyMasterForPrimaryKey(countryId, bankId, accountNo, branchCode, sendReceiveIndicator);
	}

	@Override
	@Transactional
	public List<TestKeyValues> viewTestKeyValuesForApproval() {
		return testKeyForParameterForBankDao.viewTestKeyValuesForApproval();
	}

	@Override
	@Transactional
	public String checkTestKeyValuesApproveMultiUser(BigDecimal testKeyvaluePk,	String userName) {
		return testKeyForParameterForBankDao.checkTestKeyValuesApproveMultiUser(testKeyvaluePk, userName);
	}

}
