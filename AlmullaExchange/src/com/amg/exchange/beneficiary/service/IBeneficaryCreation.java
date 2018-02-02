package com.amg.exchange.beneficiary.service;

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

public interface IBeneficaryCreation {

	List<CountryMasterDesc> checkCountryCode(String countryTelCode);

	List<BeneficaryRelationship> isBenificaryRelationExist(BigDecimal beneficaryMasterSeqId, BigDecimal relationId);

	BigDecimal checkMasterSequenceExist(BigDecimal customerNo, BigDecimal relationId);

	List<BenificiaryListView> getBeneficaryList(BigDecimal customerNo);

	BigDecimal getTelePhoneNumber(BigDecimal beneficaryMasterSeqId);

	Boolean checkSelfAlreadyExistorNot(BigDecimal customerNo, BigDecimal relationsId);

	List<BeneficaryAccount> isBankAccountNumberExist(String bankAccountNumber, BigDecimal benifisBankId, BigDecimal benifisCurrencyId, BigDecimal benifisCountryId, BigDecimal servicebankBranchId);

	List<BeneficaryAccount> getCustomerBeneficaryDetailswithAccountNO(BigDecimal benificaryMasterId, String accountNo, String type);

	List<BankAccountTypeDesc> getBankAccountType(BigDecimal languageId);

	List<BankBranch> getBranchList(BigDecimal benifisBankId);

	BankBranch getBranchDetails(BigDecimal bankBranchId);

	String getBankBranchName(BigDecimal bankId, BigDecimal bankBranchId, BigDecimal countryId);

	//List<BankBranchView> getBranchListfromView(BigDecimal benifisBankId);

	BankBranchView getBranchListfromView(BigDecimal benifisBankId, BigDecimal bankBranchId);

	BankBranch getBankBranch(BigDecimal bankId, BigDecimal bankBranchId, BigDecimal countryId);
	
	public List<AccountTypeFromView>  getAccountTypeFromView(BigDecimal countryId);
	
	public List<BanksView> getBankListFromView(BigDecimal appCountryId, BigDecimal bankCountryId,BigDecimal serviceGroupId);
	
	public String getBeneDetailProce(BigDecimal benificaryMasterId,BigDecimal benifisBankId,BigDecimal beneBankBranchId,BigDecimal beneAccountSeqId ,BigDecimal benifisCurrencyId,BigDecimal customerId);

	String getTelePhoneNumberString(BigDecimal beneficaryMasterSeqId);

	Map<String, Object> checkTelephoneExist(String telephoneNumber, String countryCode, String string);

	Map<String, Object> checkTelephoneExistWithCustIdwithPhone(String telephoneNumber, BigDecimal customerNo, String countryCode, String string);

	List<BeneficaryContact> isCoustomerTelephoneExistInDB(String telephoneNumber);

	BigDecimal getMobileNumber(BigDecimal beneficaryMasterSeqId);

	List<BankBranchView> getBranchListfromViewfromState(BigDecimal benifisStateId, BigDecimal benifisBankId);

	List<StateMaster> getStatefromBranch(BigDecimal benifisBankId);

	List<StateMasterDesc> getBankStateList(List<BigDecimal> stateIdList, BigDecimal languageId);

	void deActivateMultipleMobileNumbers(List<BeneficaryContact> lstContactDetails);

	List<BanksView> getBankListFromView(BigDecimal countryId, BigDecimal benifisCountryId, BigDecimal serviceGroupId, ArrayList<String> bankIndList);

	List<RoutingHeader> getRountingBankId(BigDecimal benifisCountryId, BigDecimal currency, BigDecimal serviceGroupId, BigDecimal serviceProvider);
	
	List<RoutingDetails> getRountingBankBranch(BigDecimal benifisCountryId, BigDecimal currency, BigDecimal serviceGroupId, BigDecimal serviceProvide,BigDecimal agentMaster);

	int checkBrnachiAvailble(BigDecimal benifisBankId);

	boolean doneSingleTransaction(BigDecimal benificaryMasterId);

	BigDecimal getbeneAccountSeqId(BigDecimal benificaryMasterId,BigDecimal bankNameId, BigDecimal branchNameId,String  accountNumber,BigDecimal currencyId);

	List<BankBranchView> getBranchListfromViewwithStateMissing(	BigDecimal benifisBankId);

	List<BeneficaryContact> getMobileDetails(BigDecimal benificaryMasterId);

	List<BeneficaryContact> getTelephoneDetails(BigDecimal benificaryMasterId);

	Map<String, Object> checkTelephoneExistWithCustIdwithPhone(BigDecimal mobileNumber, BigDecimal customerNo, String countryCode, String string);
	
	public List<BenificiaryListView> fetchBeneficiaryNewRecord(BigDecimal customerId,BigDecimal beneMasterId,BigDecimal beneAccountId,Boolean editBene,BigDecimal currencyId,BigDecimal servicegroupid);

	void concurrentSaveBeneficaryDetails(BeneficaryMaster beneficaryMaster, BeneficaryContact contact, BeneficaryAccount beneficaryAccount, BeneficaryRelationship relationship);

	List<BankBranchView> serachBranch(BigDecimal bankId, String searchBranchName, String searchIFSC, String searchSwift, String searchState);

	Boolean checkAccountTypeMigrated(BigDecimal beneficiaryAccountSeqId);
	
	public List<BenificiaryListView> fetchBeneficiaryNewRecordUpdated(BigDecimal customerId,BigDecimal beneMasterId,BigDecimal beneAccountId,Boolean editBene,BigDecimal currencyId,BigDecimal servicegroupid);
		
	public List<CountryMasterDesc> fetchCountryContactCode(BigDecimal countryID);

	BeneficaryMaster getMasterDetails(BigDecimal beneficaryMasterSeqId);
	
	public String saveUpdatedBeneTelePhoneNumber(HashMap<String, Object> migrateBeneTelephone);
	
	public String checkBeneDetailsValidation(HashMap<String, Object> checkBeneDetails) throws AMGException;
	
	public List<BeneficaryAccount> getbeneAccountDetailsWithAccNo(BigDecimal benificaryMasterId,BigDecimal bankId, BigDecimal branchId,String accountNumber,BigDecimal currencyId,BigDecimal benificaryAccSeqId) throws AMGException;
	
	public List<BeneficaryAccount> getbeneAccountDetailsForCashWU(BigDecimal benificaryMasterId,BigDecimal bankId, BigDecimal branchId,BigDecimal currencyId,BigDecimal benificaryAccSeqId,BigDecimal serviceProvider) throws AMGException;

	public void deleteBeneRelationRecord(BigDecimal beneficiaryRelationShipSeqId, String status);

	void updateBeneficaryDataforWU(BeneficaryMaster beneficaryMaster, BeneficaryAccount account, BeneficaryRelationship beneficaryRelationship, BeneficaryContact contact);
	
	public List<BeneficaryAccount> getBeneAccountDetails(BigDecimal benificaryMasterId,BigDecimal currencyId,String bankCode);
	
	public List<BankBranchView> getBranchListfromViewByCountryBank(BigDecimal countryId , BigDecimal bankId);
	
	public BigDecimal getbeneAccountSeqIdForCash(BigDecimal benificaryMasterId,BigDecimal bankNameId, BigDecimal branchNameId,String  accountNumber,BigDecimal currencyId);
	
}
	