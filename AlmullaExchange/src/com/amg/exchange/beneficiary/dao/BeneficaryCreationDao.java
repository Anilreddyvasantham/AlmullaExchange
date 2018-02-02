package com.amg.exchange.beneficiary.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amg.exchange.beneficiary.model.BankBranchView;
import com.amg.exchange.beneficiary.model.BanksView;
import com.amg.exchange.common.model.BankAccountTypeDesc;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.StateMaster;
import com.amg.exchange.common.model.StateMasterDesc;
import com.amg.exchange.remittance.model.AccountTypeFromView;
import com.amg.exchange.remittance.model.BeneficaryAccount;
import com.amg.exchange.remittance.model.BeneficaryContact;
import com.amg.exchange.remittance.model.BeneficaryMaster;
import com.amg.exchange.remittance.model.BeneficaryRelationship;
import com.amg.exchange.remittance.model.BenificiaryListView;
import com.amg.exchange.remittance.model.RoutingDetails;
import com.amg.exchange.remittance.model.RoutingHeader;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.util.AMGException;

public interface BeneficaryCreationDao {
	
	public List<CountryMasterDesc>  checkCountryCode(String countryCode);

	public List<BeneficaryRelationship> isBenificaryRelationExist(BigDecimal beneficaryMasterSeqId, BigDecimal relationId);

	public BigDecimal checkMasterSequenceExist(BigDecimal customerNo, BigDecimal relationId);

	public List<BenificiaryListView> getBeneficaryList(BigDecimal customerNo);

	public BigDecimal getTelePhoneNumber(BigDecimal beneficaryMasterSeqId);
	public Boolean checkSelfAlreadyExistorNot(BigDecimal customerNo, BigDecimal relationsId);

	public List<BeneficaryAccount> isBankAccountNumberExist(String bankAccountNumber, BigDecimal benifisBankId, BigDecimal benifisCurrencyId, BigDecimal benifisCountryId, BigDecimal servicebankBranchId);

	public List<BeneficaryAccount> isBankAccountNumberExist(BigDecimal benificaryMasterId, String accountNo, String type);

	public List<BankAccountTypeDesc> getBankAccountType(BigDecimal languageId);

	public List<BankBranch> getBranchList(BigDecimal benifisBankId);

	public BankBranch getBranchDetails(BigDecimal bankBranchId);

	public List<BeneficaryAccount> getCustomerBeneficaryDetailswithAccountNO(BigDecimal benificaryMasterId, String accountNo, String type);

	public String getBankBranchName(BigDecimal bankId, BigDecimal bankBranchId, BigDecimal countryId);

	//public List<BankBranchView> getBranchListfromView(BigDecimal benifisBankId);

	public BankBranchView getBranchListfromView(BigDecimal benifisBankId, BigDecimal bankBranchId);

	public BankBranch getBankBranch(BigDecimal bankId, BigDecimal bankBranchId, BigDecimal countryId);
	
	public List<AccountTypeFromView>  getAccountTypeFromView(BigDecimal countryId);
	
	public List<BanksView> getBankListFromView(BigDecimal appCountryId, BigDecimal bankCountryId,BigDecimal serviceGroupId);
	
	//Added on 28/12/2015
	public String getBeneDetailProce(BigDecimal benificaryMasterId,BigDecimal benifisBankId,BigDecimal beneBankBranchId,BigDecimal beneAccountSeqId,BigDecimal benifisCurrencyId,BigDecimal customerId);

	public Map<String, Object> checkTelephoneExist(String telephoneNumber, String countryCode, String string);

	public Map<String, Object> checkTelephoneExistWithCustIdwithPhone(String telephoneNumber, BigDecimal customerNo, String countryCode, String string);

	public List<BeneficaryContact> isCoustomerTelephoneExistInDB(String telephoneNumber);

	public String getTelePhoneNumberString(BigDecimal beneficaryMasterSeqId);

	public BigDecimal getMobileNumber(BigDecimal beneficaryMasterSeqId);

	public List<BankBranchView> getBranchListfromViewfromState(BigDecimal benifisStateId, BigDecimal benifisBankId);

	public List<StateMaster> getStatefromBranch(BigDecimal benifisBankId);

	public List<StateMasterDesc> getBankStateList(List<BigDecimal> stateIdList, BigDecimal languageId);

	public void deActivateMultipleMobileNumbers(List<BeneficaryContact> lstContactDetails);

	public List<BanksView> getBankListFromView(BigDecimal countryId, BigDecimal benifisCountryId, BigDecimal serviceGroupId, ArrayList<String> bankIndList);

	public List<RoutingHeader> getRountingBankId(BigDecimal benifisCountryId, BigDecimal currency, BigDecimal serviceGroupId, BigDecimal serviceProvider);
	
	public List<RoutingDetails> getRountingBankBranch(BigDecimal benifisCountryId, BigDecimal currency, BigDecimal serviceGroupId, BigDecimal serviceProvider,BigDecimal agentMaster);

	public int checkBrnachiAvailble(BigDecimal benifisBankId);

	public boolean doneSingleTransaction(BigDecimal benificaryMasterId);

	public BigDecimal getbeneAccountSeqId(BigDecimal benificaryMasterId,BigDecimal bankNameId, BigDecimal branchNameId,String accountNumber,BigDecimal currencyId);

	public List<BankBranchView> getBranchListfromViewwithStateMissing(BigDecimal benifisBankId);

	public List<BeneficaryContact> getMobileDetails(BigDecimal benificaryMasterId);

	public List<BeneficaryContact> getTelephoneDetails(BigDecimal benificaryMasterId);

	public Map<String, Object> checkTelephoneExistWithCustIdwithPhone(BigDecimal mobileNumber, BigDecimal customerNo, String countryCode, String type);
	
	public List<BenificiaryListView> fetchBeneficiaryNewRecord(BigDecimal customerId,BigDecimal beneMasterId,BigDecimal beneAccountId,Boolean editBene,BigDecimal currencyId,BigDecimal servicegroupid);

	public void concurrentSaveBeneficaryDetails(BeneficaryMaster beneficaryMaster, BeneficaryContact contact, BeneficaryAccount beneficaryAccount, BeneficaryRelationship relationship);

	public List<BankBranchView> serachBranch(BigDecimal bankid, String searchBranchName, String searchIFSC, String searchSwift, String searchState);

	public Boolean checkAccountTypeMigrated(BigDecimal beneficiaryAccountSeqId);
	
	public List<BenificiaryListView> fetchBeneficiaryNewRecordUpdated(BigDecimal customerId,BigDecimal beneMasterId,BigDecimal beneAccountId,Boolean editBene,BigDecimal currencyId,BigDecimal servicegroupid);
	
	public List<CountryMasterDesc> fetchCountryContactCode(BigDecimal countryID);

	public BeneficaryMaster getMasterDetails(BigDecimal beneficaryMasterSeqId);	
	
	public String saveUpdatedBeneTelePhoneNumber(HashMap<String, Object> migrateBeneTelephone);
	
	public String checkBeneDetailsValidation(HashMap<String, Object> checkBeneDetails) throws AMGException;
	
	public List<BeneficaryAccount> getbeneAccountDetailsWithAccNo(BigDecimal benificaryMasterId,BigDecimal bankId, BigDecimal branchId,String accountNumber,BigDecimal currencyId,BigDecimal benificaryAccSeqId) throws AMGException;
	
	public List<BeneficaryAccount> getbeneAccountDetailsForCashWU(BigDecimal benificaryMasterId,BigDecimal bankId, BigDecimal branchId,BigDecimal currencyId,BigDecimal benificaryAccSeqId,BigDecimal serviceProvider) throws AMGException;

	public void deleteBeneRelationRecord(BigDecimal beneficiaryRelationShipSeqId, String status);

	public void updateBeneficaryDataforWU(BeneficaryMaster beneficaryMaster,BeneficaryAccount account, BeneficaryRelationship beneficaryRelationship, BeneficaryContact contact);
	
	public List<BeneficaryAccount> getBeneAccountDetails(BigDecimal benificaryMasterId,BigDecimal currencyId,String bankCode);
	
	public List<BankBranchView> getBranchListfromViewByCountryBank(BigDecimal countryId , BigDecimal bankId);
	
	public BigDecimal getbeneAccountSeqIdForCash(BigDecimal benificaryMasterId,BigDecimal bankNameId, BigDecimal branchNameId,String  accountNumber,BigDecimal currencyId);
	
}
