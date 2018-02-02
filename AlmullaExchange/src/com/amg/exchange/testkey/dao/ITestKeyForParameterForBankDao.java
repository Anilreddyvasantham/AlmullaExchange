package com.amg.exchange.testkey.dao;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.common.model.BankAccountType;
import com.amg.exchange.common.model.BankAccountTypeDesc;
import com.amg.exchange.testkey.bean.TestKeyValueAccNumDesc;
import com.amg.exchange.testkey.model.TestKeyMaster;
import com.amg.exchange.testkey.model.TestKeyValues;
import com.amg.exchange.testkey.model.ViewTestKeyParameter;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.CurrencyMaster;

public interface ITestKeyForParameterForBankDao {

	public List<BankApplicability> toFetchCorpondingAllBank(BigDecimal countryId);

	public List<BankAccountDetails> toFetchAccountNumberBasedOnBank(BigDecimal bankId);

	public List<BankAccountDetails> toFetchCurrencyBasedOnAccountNumber(BigDecimal accountNo);

	public List<BankBranch> toFetchBranchBasedOnBank(BigDecimal bankId);

	public List<BankAccountTypeDesc> tofetchBankAccountTypeDescBasedOnAccountNo(BigDecimal bankAccoutTypeId, BigDecimal languageId);

	public List<ViewTestKeyParameter> tofetchAllPrimaryParameters();

	public List<TestKeyMaster> fetchtheRecordFromTestKeyMaster(BigDecimal countryId, BigDecimal bankId, String accountNo, BigDecimal branchCode, String sendReceiveIndicator);

	public List<TestKeyMaster> toViewForAllrecordsTofetchDb();

	// to fetch Test Key Name
	public String toFetchParameterTestKeyName(String primaryTestKey);

	// to fetch key tkytyp
	public String tofetchAllPrimaryParameters(String primaryTestKey);

	// save
	public void saveTestKeyMaster(TestKeyMaster testKeyMaster);

	public String toFetchBankBranch(BigDecimal bankBranchId);

	public BigDecimal tofetchAccountId(String bankAccountNumber, BigDecimal bankId);

	public void deleteRecordTestKeyMaster(BigDecimal testKeyParameterPk);

	public void deActivateRecord(BigDecimal testKeyParameterPk, String userName);

	// ----------------------Test Key Values Services-----------------//

	// to fetch account type description by language id
	public List<BankAccountTypeDesc> fetchAccountDescTypeBasedonLanguageId(BigDecimal languageId);

	// save Test key Values
	public Boolean saveTestKeyValues(List<TestKeyValues> lstTestKeyValues);

	// fetching all Test key values for View
	public List<TestKeyValues> viewAllTestKeyValues();

	// fetch currency id by Currency Name
	public List<CurrencyMaster> tofetchCurrencyId(String currencyName);

	// fetch Account id and Account type Id
	public List<BankAccountDetails> tofetchAccountIdAccountTypeId(String bankAccountNumber, BigDecimal bankId);
	
	// fetch records based on combinations
	public List<TestKeyValues> fetchtheRecordFromTestKeyValue(BigDecimal appcountryId, BigDecimal bankId, String accountNo, BigDecimal branchCode, String sendReceiveIndicator);
	
	// de-active record in test key values
	public void deActivateRecordTestKeyValues(BigDecimal testKeyvaluePk, String userName);
	
	// delete record from test key values
	public void deleteRecordTestKeyValues(BigDecimal testKeyvaluePk);
	
	// activated record to deactivate 
	public void activatedTestKeyValues(BigDecimal testKeyvaluePk, String userName, String remarks);
	
	//fetch primary key parameter from test key master
	public List<TestKeyMaster> fetchRecordFromTestKeyMasterForPrimaryKey(BigDecimal countryId, BigDecimal bankId, String accountNo, BigDecimal branchCode, String sendReceiveIndicator);
	
	// fetching all Test key values for View
	public List<TestKeyValues> viewTestKeyValuesForApproval();
	
	// checking record approved or not
	public String checkTestKeyValuesApproveMultiUser(BigDecimal testKeyvaluePk, String userName);

	// ---------Test Key Master Approval Services-----------//
	public List<TestKeyMaster> toApprovalForAllrecordsTofetchDb();

	public String checkTestKeyMasterApproveMultiUser(BigDecimal testKeyParameterPk, String userName);

}
