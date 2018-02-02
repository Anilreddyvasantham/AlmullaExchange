package com.amg.exchange.beneficiary.serviceimpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.beneficiary.dao.BeneficaryCreationDao;
import com.amg.exchange.beneficiary.model.BankBranchView;
import com.amg.exchange.beneficiary.model.BanksView;
import com.amg.exchange.beneficiary.service.IBeneficaryCreation;
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

@Service("beneficaryCreationService")
@Transactional
public class BeneficaryCreationServiceImpl implements IBeneficaryCreation{

	@Autowired
	BeneficaryCreationDao beneficaryCreationDao;

	@Override
	public List<CountryMasterDesc> checkCountryCode(String countryTelCode) {
		return beneficaryCreationDao.checkCountryCode(countryTelCode);
	}

	@Override
	public List<BeneficaryRelationship> isBenificaryRelationExist(BigDecimal beneficaryMasterSeqId, BigDecimal relationId) {
		return beneficaryCreationDao.isBenificaryRelationExist(beneficaryMasterSeqId,relationId);
	}

	@Override
	public BigDecimal checkMasterSequenceExist(BigDecimal customerNo, BigDecimal relationId) {
		return beneficaryCreationDao.checkMasterSequenceExist(customerNo,relationId);
	}

	@Override
	public List<BenificiaryListView> getBeneficaryList(BigDecimal customerNo) {
		return beneficaryCreationDao.getBeneficaryList(customerNo);
	}

	@Override
	public BigDecimal getTelePhoneNumber(BigDecimal beneficaryMasterSeqId) {
		return beneficaryCreationDao.getTelePhoneNumber(beneficaryMasterSeqId);
	}

	@Override
	public Boolean checkSelfAlreadyExistorNot(BigDecimal customerNo, BigDecimal relationsId) {
		return beneficaryCreationDao.checkSelfAlreadyExistorNot(customerNo, relationsId);
	}

	@Override
	public List<BeneficaryAccount> isBankAccountNumberExist(String bankAccountNumber, BigDecimal benifisBankId, BigDecimal benifisCurrencyId, BigDecimal benifisCountryId, BigDecimal servicebankBranchId) {
		return beneficaryCreationDao.isBankAccountNumberExist(bankAccountNumber,benifisBankId,benifisCurrencyId,benifisCountryId,servicebankBranchId);
	}

	@Override
	public List<BeneficaryAccount> getCustomerBeneficaryDetailswithAccountNO(BigDecimal benificaryMasterId, String accountNo, String type) {
		return beneficaryCreationDao.getCustomerBeneficaryDetailswithAccountNO(benificaryMasterId,accountNo,type);
	}

	@Override
	public List<BankAccountTypeDesc> getBankAccountType(BigDecimal languageId) {
		return beneficaryCreationDao.getBankAccountType(languageId);
	}

	@Override
	public List<BankBranch> getBranchList(BigDecimal benifisBankId) {
		return beneficaryCreationDao.getBranchList(benifisBankId);

	}

	@Override
	public BankBranch getBranchDetails(BigDecimal bankBranchId) {
		return beneficaryCreationDao.getBranchDetails(bankBranchId);
	}

	@Override
	public String getBankBranchName(BigDecimal bankId, BigDecimal bankBranchId, BigDecimal countryId) {
		return beneficaryCreationDao.getBankBranchName(bankId,bankBranchId,countryId);
	}

	/*@Override
	public List<BankBranchView> getBranchListfromView(BigDecimal benifisBankId) {
		return beneficaryCreationDao.getBranchListfromView(benifisBankId);
	}*/


	@Override
	public BankBranch getBankBranch(BigDecimal bankId, BigDecimal bankBranchId, BigDecimal countryId) {
		return beneficaryCreationDao.getBankBranch(bankId,bankBranchId,countryId);
	}

	@Override
	public BankBranchView getBranchListfromView(BigDecimal benifisBankId,BigDecimal bankBranchId) {
		return beneficaryCreationDao.getBranchListfromView(benifisBankId, bankBranchId);
	}

	@Override
	public List<AccountTypeFromView> getAccountTypeFromView(BigDecimal countryId) {
		return beneficaryCreationDao.getAccountTypeFromView(countryId);
	}

	@Override
	public List<BanksView> getBankListFromView(BigDecimal appCountryId,BigDecimal bankCountryId, BigDecimal serviceGroupId) {
		return beneficaryCreationDao.getBankListFromView(appCountryId, bankCountryId, serviceGroupId);
	}

	@Override
	public String getBeneDetailProce(BigDecimal benificaryMasterId, BigDecimal benifisBankId, BigDecimal beneBankBranchId,
			BigDecimal beneAccountSeqId,BigDecimal benifisCurrencyId, BigDecimal customerId) {
		return beneficaryCreationDao.getBeneDetailProce(benificaryMasterId, benifisBankId, beneBankBranchId, beneAccountSeqId,benifisCurrencyId, customerId);
	}

	@Override
	public String getTelePhoneNumberString(BigDecimal beneficaryMasterSeqId) {
		return beneficaryCreationDao.getTelePhoneNumberString(beneficaryMasterSeqId);
	}

	@Override
	public Map<String, Object> checkTelephoneExist(String telephoneNumber, String countryCode, String string) {
		return beneficaryCreationDao.checkTelephoneExist(telephoneNumber,countryCode,string);
	}

	@Override
	public Map<String, Object> checkTelephoneExistWithCustIdwithPhone(String telephoneNumber, BigDecimal customerNo, String countryCode, String string) {
		return beneficaryCreationDao.checkTelephoneExistWithCustIdwithPhone(telephoneNumber,customerNo,countryCode,string);
	}

	@Override
	public List<BeneficaryContact> isCoustomerTelephoneExistInDB(String telephoneNumber) {
		return beneficaryCreationDao.isCoustomerTelephoneExistInDB(telephoneNumber);
	}

	@Override
	public BigDecimal getMobileNumber(BigDecimal beneficaryMasterSeqId) {
		return beneficaryCreationDao.getMobileNumber(beneficaryMasterSeqId);
	}

	@Override
	public List<BankBranchView> getBranchListfromViewfromState(BigDecimal benifisStateId,BigDecimal benifisBankId) {
		return beneficaryCreationDao.getBranchListfromViewfromState(benifisStateId,benifisBankId);
	}

	@Override
	public List<StateMaster> getStatefromBranch(BigDecimal benifisBankId) {
		return beneficaryCreationDao.getStatefromBranch(benifisBankId);
	}

	@Override
	public List<StateMasterDesc> getBankStateList(List<BigDecimal> stateIdList,BigDecimal languageId) {
		return beneficaryCreationDao.getBankStateList(stateIdList,languageId);
	}

	@Override
	public void deActivateMultipleMobileNumbers(List<BeneficaryContact> lstContactDetails) {
		beneficaryCreationDao.deActivateMultipleMobileNumbers(lstContactDetails);
	}

	@Override
	public List<BanksView> getBankListFromView(BigDecimal countryId, BigDecimal benifisCountryId, BigDecimal serviceGroupId, ArrayList<String> bankIndList) {
		return beneficaryCreationDao.getBankListFromView(countryId,benifisCountryId,serviceGroupId,bankIndList);
	}

	@Override
	public List<RoutingHeader>  getRountingBankId(BigDecimal benifisCountryId, BigDecimal currency, BigDecimal serviceGroupId, BigDecimal serviceProvider) {
		return beneficaryCreationDao.getRountingBankId(benifisCountryId,currency,serviceGroupId,serviceProvider);
	}

	@Override
	public int checkBrnachiAvailble(BigDecimal benifisBankId) {
		return beneficaryCreationDao.checkBrnachiAvailble(benifisBankId);
	}

	@Override
	public List<RoutingDetails> getRountingBankBranch(BigDecimal benifisCountryId, BigDecimal currency,	BigDecimal serviceGroupId, BigDecimal serviceProvider,BigDecimal agentMaster) {
		return beneficaryCreationDao.getRountingBankBranch(benifisCountryId, currency, serviceGroupId, serviceProvider,agentMaster);
	}

	@Override
	public boolean doneSingleTransaction(BigDecimal benificaryMasterId) {
		// TODO Auto-generated method stub
		return beneficaryCreationDao.doneSingleTransaction(benificaryMasterId);
	}

	@Override
	public BigDecimal getbeneAccountSeqId(BigDecimal benificaryMasterId,BigDecimal bankNameId, BigDecimal branchNameId,	String accountNumber,BigDecimal currencyId) {
		return beneficaryCreationDao.getbeneAccountSeqId(benificaryMasterId,bankNameId,branchNameId,accountNumber,currencyId);
	}

	@Override
	public List<BankBranchView> getBranchListfromViewwithStateMissing(BigDecimal benifisBankId) {
		return beneficaryCreationDao.getBranchListfromViewwithStateMissing(benifisBankId);
	}

	@Override
	public List<BeneficaryContact> getMobileDetails(BigDecimal benificaryMasterId) {
		return beneficaryCreationDao.getMobileDetails(benificaryMasterId);
	}

	@Override
	public List<BeneficaryContact> getTelephoneDetails(BigDecimal benificaryMasterId) {
		return beneficaryCreationDao.getTelephoneDetails(benificaryMasterId);
	}

	@Override
	public Map<String, Object> checkTelephoneExistWithCustIdwithPhone(BigDecimal mobileNumber, BigDecimal customerNo, String countryCode, String type) {
		return beneficaryCreationDao.checkTelephoneExistWithCustIdwithPhone(mobileNumber,customerNo,countryCode,type);
	}

	@Override
	public List<BenificiaryListView> fetchBeneficiaryNewRecord(BigDecimal customerId, BigDecimal beneMasterId,BigDecimal beneAccountId,Boolean editBene,BigDecimal currencyId,BigDecimal servicegroupid) {
		return beneficaryCreationDao.fetchBeneficiaryNewRecord(customerId, beneMasterId, beneAccountId,editBene, currencyId, servicegroupid);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void concurrentSaveBeneficaryDetails(BeneficaryMaster beneficaryMaster, BeneficaryContact contact, BeneficaryAccount beneficaryAccount, BeneficaryRelationship relationship) {
		beneficaryCreationDao.concurrentSaveBeneficaryDetails(beneficaryMaster,contact,beneficaryAccount,relationship);
	}

	@Override
	@Transactional
	public List<BankBranchView> serachBranch(BigDecimal bankid,String searchBranchName, String searchIFSC, String searchSwift, String searchState) {
		return beneficaryCreationDao.serachBranch(bankid,searchBranchName,searchIFSC,searchSwift,searchState);
	}

	@Override
	@Transactional
	public Boolean checkAccountTypeMigrated(BigDecimal beneficiaryAccountSeqId) {
		return beneficaryCreationDao.checkAccountTypeMigrated(beneficiaryAccountSeqId);
	}

	@Override
	@Transactional
	public List<BenificiaryListView> fetchBeneficiaryNewRecordUpdated(BigDecimal customerId,BigDecimal beneMasterId,BigDecimal beneAccountId,Boolean editBene,BigDecimal currencyId,BigDecimal servicegroupid) {
		return beneficaryCreationDao.fetchBeneficiaryNewRecordUpdated(customerId, beneMasterId, beneAccountId, editBene, currencyId, servicegroupid);
	}

	@Override
	@Transactional
	public List<CountryMasterDesc> fetchCountryContactCode(BigDecimal countryID) {
		return beneficaryCreationDao.fetchCountryContactCode(countryID);
	}

	@Override
	@Transactional
	public BeneficaryMaster getMasterDetails(BigDecimal beneficaryMasterSeqId) {
		return beneficaryCreationDao.getMasterDetails(beneficaryMasterSeqId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String saveUpdatedBeneTelePhoneNumber(HashMap<String, Object> migrateBeneTelephone) {
		return beneficaryCreationDao.saveUpdatedBeneTelePhoneNumber(migrateBeneTelephone);
	}

	@Override
	@Transactional
	public String checkBeneDetailsValidation(HashMap<String, Object> checkBeneDetails) throws AMGException{
		return beneficaryCreationDao.checkBeneDetailsValidation(checkBeneDetails);
	}

	@Override
	@Transactional
	public List<BeneficaryAccount> getbeneAccountDetailsWithAccNo(BigDecimal benificaryMasterId, BigDecimal bankId,	BigDecimal branchId, String accountNumber, BigDecimal currencyId, BigDecimal benificaryAccSeqId) throws AMGException{
		return beneficaryCreationDao.getbeneAccountDetailsWithAccNo(benificaryMasterId, bankId, branchId, accountNumber, currencyId, benificaryAccSeqId);
	}

	@Override
	@Transactional
	public List<BeneficaryAccount> getbeneAccountDetailsForCashWU(BigDecimal benificaryMasterId, BigDecimal bankId,	BigDecimal branchId, BigDecimal currencyId,	BigDecimal benificaryAccSeqId, BigDecimal serviceProvider) throws AMGException{
		return beneficaryCreationDao.getbeneAccountDetailsForCashWU(benificaryMasterId, bankId, branchId, currencyId, benificaryAccSeqId, serviceProvider);
	}

	@Override
	@Transactional
	public void deleteBeneRelationRecord(BigDecimal beneficiaryRelationShipSeqId, String status) {
		 beneficaryCreationDao.deleteBeneRelationRecord(beneficiaryRelationShipSeqId,status);
	}

	@Override
	@Transactional
	public void updateBeneficaryDataforWU(BeneficaryMaster beneficaryMaster,BeneficaryAccount account, BeneficaryRelationship beneficaryRelationship, BeneficaryContact contact) {
		beneficaryCreationDao.updateBeneficaryDataforWU(beneficaryMaster,account,beneficaryRelationship,contact);
	}

	@Override
	@Transactional
	public List<BeneficaryAccount> getBeneAccountDetails(BigDecimal benificaryMasterId, BigDecimal currencyId,String bankCode) {
		return beneficaryCreationDao.getBeneAccountDetails(benificaryMasterId, currencyId, bankCode);
	}

	@Override
	@Transactional
	public List<BankBranchView> getBranchListfromViewByCountryBank(BigDecimal countryId, BigDecimal bankId) {
		return beneficaryCreationDao.getBranchListfromViewByCountryBank(countryId, bankId);
	}

	@Override
	@Transactional
	public BigDecimal getbeneAccountSeqIdForCash(BigDecimal benificaryMasterId,
			BigDecimal bankNameId, BigDecimal branchNameId,
			String accountNumber, BigDecimal currencyId) {
		return beneficaryCreationDao.getbeneAccountSeqIdForCash(benificaryMasterId, bankNameId, branchNameId, accountNumber, currencyId);
	}
	

}
