package com.amg.exchange.testkey.daoImpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.model.BankAccountTypeDesc;
import com.amg.exchange.complaint.model.ComplaintAssigned;
import com.amg.exchange.testkey.dao.ITestKeyForParameterForBankDao;
import com.amg.exchange.testkey.model.TestKeyMaster;
import com.amg.exchange.testkey.model.TestKeyValues;
import com.amg.exchange.testkey.model.ViewTestKeyParameter;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankIndicator;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.util.Constants;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Repository
public class TestKeyForParameterForBankDaoImpl extends CommonDaoImpl implements ITestKeyForParameterForBankDao {

	@Override
	public List<BankApplicability> toFetchCorpondingAllBank(BigDecimal countryId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BankApplicability.class, "bankApplicability");
		criteria.setFetchMode("bankApplicability.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("bankApplicability.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));

		criteria.setFetchMode("bankApplicability.bankInd", FetchMode.JOIN);
		criteria.createAlias("bankApplicability.bankInd", "bankInd", JoinType.INNER_JOIN);

		BigDecimal corresBankIndicatorId = fetchLocalBankIndicator(Constants.BANK_INDICATOR_CORR_BANK);
		if (corresBankIndicatorId != null) {
			criteria.add(Restrictions.eq("bankInd.bankIndicatorId", corresBankIndicatorId));
		}
		criteria.setFetchMode("bankApplicability.bankMaster", FetchMode.JOIN);
		criteria.createAlias("bankApplicability.bankMaster", "bankMaster", JoinType.INNER_JOIN);
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BankApplicability> lstbankApplicability = (List<BankApplicability>) findAll(criteria);
		return lstbankApplicability;
	}

	public BigDecimal fetchLocalBankIndicator(String bankIndicatorCode) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankIndicator.class, "bankIndicator");
		dCriteria.add(Restrictions.eq("bankIndicator.bankIndicatorCode", bankIndicatorCode));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BankIndicator> lstBankIndId = (List<BankIndicator>) findAll(dCriteria);
		if (lstBankIndId.size() != 0) {
			return ((List<BankIndicator>) findAll(dCriteria)).get(0).getBankIndicatorId();
		}

		return null;
	}

	@Override
	public List<BankAccountDetails> toFetchAccountNumberBasedOnBank(BigDecimal bankId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BankAccountDetails.class, "bankAccountDetails");
		criteria.setFetchMode("bankAccountDetails.exBankMaster", FetchMode.JOIN);
		criteria.createAlias("bankAccountDetails.exBankMaster", "exBankMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("exBankMaster.bankId", bankId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BankAccountDetails> lstBankAccountDetails = (List<BankAccountDetails>) findAll(criteria);
		return lstBankAccountDetails;
	}

	@Override
	public List<BankAccountDetails> toFetchCurrencyBasedOnAccountNumber(BigDecimal accountNo) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BankAccountDetails.class, "bankAccountDetails");
		criteria.setFetchMode("bankAccountDetails.fsCurrencyMaster", FetchMode.JOIN);
		criteria.createAlias("bankAccountDetails.fsCurrencyMaster", "fsCurrencyMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("bankAccountDetails.bankAcctDetId", accountNo));
		criteria.setFetchMode("bankAccountDetails.bankAccountType", FetchMode.JOIN);
		criteria.createAlias("bankAccountDetails.bankAccountType", "bankAccountType", JoinType.INNER_JOIN);
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BankAccountDetails> lstBankAccountDetails = (List<BankAccountDetails>) findAll(criteria);
		return lstBankAccountDetails;
	}

	@Override
	public List<BankBranch> toFetchBranchBasedOnBank(BigDecimal bankId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BankBranch.class, "bankBranch");
		criteria.setFetchMode("bankBranch.exBankMaster", FetchMode.JOIN);
		criteria.createAlias("bankBranch.exBankMaster", "exBankMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("exBankMaster.bankId", bankId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BankBranch> lstBankBranch = (List<BankBranch>) findAll(criteria);
		return lstBankBranch;
	}

	@Override
	public List<BankAccountTypeDesc> tofetchBankAccountTypeDescBasedOnAccountNo(BigDecimal bankAccoutTypeId, BigDecimal languageId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BankAccountTypeDesc.class, "bankAccountTypeDesc");
		criteria.setFetchMode("bankAccountTypeDesc.bankAccountTypeId", FetchMode.JOIN);
		criteria.createAlias("bankAccountTypeDesc.bankAccountTypeId", "bankAccountTypeId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("bankAccountTypeId.bankAccountTypeId", bankAccoutTypeId));
		criteria.setFetchMode("bankAccountTypeDesc.languageId", FetchMode.JOIN);
		criteria.createAlias("bankAccountTypeDesc.languageId", "languageId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageId.languageId", languageId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BankAccountTypeDesc> lstBankAccountTypeDesc = (List<BankAccountTypeDesc>) findAll(criteria);
		return lstBankAccountTypeDesc;
	}

	@Override
	public List<ViewTestKeyParameter> tofetchAllPrimaryParameters() {
		DetachedCriteria criteria = DetachedCriteria.forClass(ViewTestKeyParameter.class);
		//criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<ViewTestKeyParameter> lstViewTestKeyParameters = (List<ViewTestKeyParameter>) findAll(criteria);
		return lstViewTestKeyParameters;
	}

	@Override
	public List<TestKeyMaster> fetchtheRecordFromTestKeyMaster(BigDecimal countryId, BigDecimal bankId, String accountNo, BigDecimal branchCode, String sendReceiveIndicator) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TestKeyMaster.class, "testKeyMaster");
		// application country
		criteria.setFetchMode("testKeyMaster.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("testKeyMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		// Bank Master Id
		criteria.setFetchMode("testKeyMaster.exBankMaster", FetchMode.JOIN);
		criteria.createAlias("testKeyMaster.exBankMaster", "exBankMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("exBankMaster.bankId", bankId));
		// BankAccountDetails
		criteria.add(Restrictions.eq("testKeyMaster.bankAccountNumber", accountNo));
		// BankBranchCode
		criteria.setFetchMode("testKeyMaster.exBankBranch", FetchMode.JOIN);
		criteria.createAlias("testKeyMaster.exBankBranch", "exBankBranch", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("exBankBranch.bankBranchId", branchCode));
		// sendOrReceieveIndicator
		criteria.add(Restrictions.eq("testKeyMaster.sendReceieveIndicator", sendReceiveIndicator));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<TestKeyMaster> lstTestKeyMasters = (List<TestKeyMaster>) findAll(criteria);
		return lstTestKeyMasters;
	}

	@Override
	public List<TestKeyMaster> toViewForAllrecordsTofetchDb() {
		DetachedCriteria criteria = DetachedCriteria.forClass(TestKeyMaster.class);
		List<TestKeyMaster> lstTestKeyMaster = (List<TestKeyMaster>) findAll(criteria);
		return lstTestKeyMaster;
	}

	@Override
	public List<BankAccountTypeDesc> fetchAccountDescTypeBasedonLanguageId(BigDecimal languageId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BankAccountTypeDesc.class, "bankAccountTypeDesc");
		criteria.setFetchMode("bankAccountTypeDesc.languageId", FetchMode.JOIN);
		criteria.createAlias("bankAccountTypeDesc.languageId", "languageId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageId.languageId", languageId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BankAccountTypeDesc> lstBankAccountTypeDesc = (List<BankAccountTypeDesc>) findAll(criteria);
		return lstBankAccountTypeDesc;
	}

	@Override
	public Boolean saveTestKeyValues(List<TestKeyValues> lstTestKeyValues) {
		// TODO Auto-generated method stub
		Boolean saveBoolean = false;

		try {
			if (lstTestKeyValues.size() != 0) {
				for (TestKeyValues testKeyValues : lstTestKeyValues) {
					getSession().saveOrUpdate(testKeyValues);
				}
			}
			saveBoolean = true;
		} catch (Exception e) {
			// TODO: handle exception
			saveBoolean = false;
			System.out.println(e.getMessage());
		}

		return saveBoolean;
	}


	@Override
	public List<TestKeyValues> viewAllTestKeyValues() {
		DetachedCriteria criteria = DetachedCriteria.forClass(TestKeyValues.class, "testKeyValues");
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		criteria.addOrder(Order.asc("testKeyValues.exbankMasterId.bankId"));
		List<TestKeyValues> lstTestKeyValues = (List<TestKeyValues>) findAll(criteria);
		return lstTestKeyValues;
	}

	@Override
	public String toFetchParameterTestKeyName(String primaryTestKey) {
		String returnStr = "";
		DetachedCriteria criteria = DetachedCriteria.forClass(ViewTestKeyParameter.class, "viewTestKeyParameter");
		criteria.add(Restrictions.eq("viewTestKeyParameter.paramCodeDef", primaryTestKey));
		List<ViewTestKeyParameter> lstViewTestKeyParameter = (List<ViewTestKeyParameter>) findAll(criteria);
		if(lstViewTestKeyParameter!=null && lstViewTestKeyParameter.size()>0){
			returnStr=  lstViewTestKeyParameter.get(0).getFullDesc();
		}
		return returnStr;
	}

	@Override
	public String tofetchAllPrimaryParameters(String primaryTestKey) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ViewTestKeyParameter.class, "viewTestKeyParameter");
		criteria.add(Restrictions.eq("viewTestKeyParameter.paramCodeDef", primaryTestKey));
		List<ViewTestKeyParameter> lstViewTestKeyParameter = (List<ViewTestKeyParameter>) findAll(criteria);
		return lstViewTestKeyParameter.get(0).getTkytyp();
	}

	@Override
	public void saveTestKeyMaster(TestKeyMaster testKeyMaster) {
		getSession().saveOrUpdate(testKeyMaster);
	}

	@Override
	public String toFetchBankBranch(BigDecimal bankBranchId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BankBranch.class, "bankBranch");
		criteria.add(Restrictions.eq("bankBranch.bankBranchId", bankBranchId));
		List<BankBranch> lstBankBranch = (List<BankBranch>) findAll(criteria);
		return lstBankBranch.get(0).getBranchFullName();
	}

	@Override
	public BigDecimal tofetchAccountId(String bankAccountNumber, BigDecimal bankId) {
		BankAccountDetails lstBankAccDet = null;
		BigDecimal lstempty = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(BankAccountDetails.class, "bankAccountDetails");
		criteria.add(Restrictions.eq("bankAccountDetails.bankAcctNo", bankAccountNumber));
		criteria.setFetchMode("bankAccountDetails.exBankMaster", FetchMode.JOIN);
		criteria.createAlias("bankAccountDetails.exBankMaster", "exBankMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("exBankMaster.bankId", bankId));
		List<BankAccountDetails> lstBankAccountDetails = (List<BankAccountDetails>) findAll(criteria);
		if(lstBankAccountDetails.size() != 0){
			lstBankAccDet = lstBankAccountDetails.get(0);

			return lstBankAccDet.getBankAcctDetId();
		}


		return  lstempty;
	}

	@Override
	public void deleteRecordTestKeyMaster(BigDecimal testKeyParameterPk) {
		TestKeyMaster testKeyMaster = (TestKeyMaster) getSession().get(TestKeyMaster.class, testKeyParameterPk);
		getSession().delete(testKeyMaster);
	}

	@Override
	public void deActivateRecord(BigDecimal testKeyParameterPk, String userName) {
		TestKeyMaster testKeyMaster = (TestKeyMaster) getSession().get(TestKeyMaster.class, testKeyParameterPk);
		testKeyMaster.setIsActive(Constants.U);
		testKeyMaster.setModifiedBy(userName);
		testKeyMaster.setModifiedDate(new Date());
		testKeyMaster.setApprovedBy(null);
		testKeyMaster.setApprovedDate(null);
		testKeyMaster.setRemarks(null);
		getSession().saveOrUpdate(testKeyMaster);
	}

	@Override
	public List<CurrencyMaster> tofetchCurrencyId(String currencyName) {

		DetachedCriteria criteria = DetachedCriteria.forClass(CurrencyMaster.class, "currencyMaster");
		criteria.add(Restrictions.eq("currencyMaster.currencyName", currencyName));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CurrencyMaster> lstCurrencyMaster = (List<CurrencyMaster>) findAll(criteria);

		return lstCurrencyMaster;
	}

	@Override
	public List<BankAccountDetails> tofetchAccountIdAccountTypeId(String bankAccountNumber, BigDecimal bankId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BankAccountDetails.class, "bankAccountDetails");
		criteria.add(Restrictions.eq("bankAccountDetails.bankAcctNo", bankAccountNumber));
		criteria.setFetchMode("bankAccountDetails.exBankMaster", FetchMode.JOIN);
		criteria.createAlias("bankAccountDetails.exBankMaster", "exBankMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("exBankMaster.bankId", bankId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BankAccountDetails> lstBankAccountDetails = (List<BankAccountDetails>) findAll(criteria);

		return  lstBankAccountDetails;
	}

	@Override
	public List<TestKeyMaster> toApprovalForAllrecordsTofetchDb() {
		DetachedCriteria criteria = DetachedCriteria.forClass(TestKeyMaster.class,"testKeyMaster");
		criteria.add(Restrictions.eq("testKeyMaster.isActive", Constants.U));
		List<TestKeyMaster> lstTestKeyMaster = (List<TestKeyMaster>) findAll(criteria);
		return lstTestKeyMaster;
	}

	@Override
	public String checkTestKeyMasterApproveMultiUser(BigDecimal testKeyParameterPk, String userName) {
		String approvalMsg;
		TestKeyMaster testKeyMaster=(TestKeyMaster) getSession().get(TestKeyMaster.class, testKeyParameterPk);
		String approvalUser=testKeyMaster.getApprovedBy();
		if(approvalUser==null){
			testKeyMaster.setIsActive(Constants.Yes);
			testKeyMaster.setApprovedBy(userName);
			testKeyMaster.setApprovedDate(new Date());
			getSession().update(testKeyMaster);
			approvalMsg="Success";
		}else{
			approvalMsg="Fail";      
		}
		return approvalMsg;
	}

	@Override
	public List<TestKeyValues> fetchtheRecordFromTestKeyValue(BigDecimal appcountryId, BigDecimal bankId, String accountNo, BigDecimal branchCode, String sendReceiveIndicator) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TestKeyValues.class, "testKeyValues");
		// application country
		criteria.setFetchMode("testKeyValues.fsApplicationCountryId", FetchMode.JOIN);
		criteria.createAlias("testKeyValues.fsApplicationCountryId", "fsApplicationCountryId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsApplicationCountryId.countryId", appcountryId));
		// Bank Master Id
		criteria.setFetchMode("testKeyValues.exbankMasterId", FetchMode.JOIN);
		criteria.createAlias("testKeyValues.exbankMasterId", "exbankMasterId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("exbankMasterId.bankId", bankId));
		// BankAccountDetails
		criteria.add(Restrictions.eq("testKeyValues.bankAccountNumber", accountNo));
		// BankBranchCode
		criteria.add(Restrictions.eq("testKeyValues.bankBranchCode", branchCode));
		// sendOrReceieveIndicator
		criteria.add(Restrictions.eq("testKeyValues.sendReceiveIndicator", sendReceiveIndicator));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<TestKeyValues> lstTestKeyValues = (List<TestKeyValues>) findAll(criteria);
		return lstTestKeyValues;
	}

	@Override
	public void deActivateRecordTestKeyValues(BigDecimal testKeyvaluePk,String userName) {
		TestKeyValues testKeyvalues = (TestKeyValues) getSession().get(TestKeyValues.class, testKeyvaluePk);
		testKeyvalues.setIsActive(Constants.U);
		testKeyvalues.setModifiedBy(userName);
		testKeyvalues.setModifiedDate(new Date());
		testKeyvalues.setApprovedBy(null);
		testKeyvalues.setApprovedDate(null);
		testKeyvalues.setRemarks(null);
		getSession().saveOrUpdate(testKeyvalues);
	}

	@Override
	public void deleteRecordTestKeyValues(BigDecimal testKeyvaluePk) {
		TestKeyValues testKeyvalues = (TestKeyValues) getSession().get(TestKeyValues.class, testKeyvaluePk);
		getSession().delete(testKeyvalues);
	}

	@Override
	public void activatedTestKeyValues(BigDecimal testKeyvaluePk, String userName, String remarks) {
		TestKeyValues testKeyvalues = (TestKeyValues) getSession().get(TestKeyValues.class, testKeyvaluePk);
		testKeyvalues.setIsActive(Constants.D);
		testKeyvalues.setModifiedBy(userName);
		testKeyvalues.setModifiedDate(new Date());
		testKeyvalues.setApprovedBy(null);
		testKeyvalues.setApprovedDate(null);
		testKeyvalues.setRemarks(remarks);
		getSession().saveOrUpdate(testKeyvalues);
	}
	
	@Override
	public List<TestKeyMaster> fetchRecordFromTestKeyMasterForPrimaryKey(BigDecimal countryId, BigDecimal bankId, String accountNo, BigDecimal branchCode, String sendReceiveIndicator) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TestKeyMaster.class, "testKeyMaster");
		// application country
		criteria.setFetchMode("testKeyMaster.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("testKeyMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		// Bank Master Id
		criteria.setFetchMode("testKeyMaster.exBankMaster", FetchMode.JOIN);
		criteria.createAlias("testKeyMaster.exBankMaster", "exBankMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("exBankMaster.bankId", bankId));
		// BankAccountDetails
		criteria.add(Restrictions.eq("testKeyMaster.bankAccountNumber", accountNo));
		// BankBranchCode
		criteria.add(Restrictions.eq("testKeyMaster.branchCode", branchCode));
		// sendOrReceieveIndicator
		criteria.add(Restrictions.eq("testKeyMaster.sendReceieveIndicator", sendReceiveIndicator));
		criteria.add(Restrictions.eq("testKeyMaster.isActive", Constants.Yes));
		List<TestKeyMaster> lstTestKeyMasters = (List<TestKeyMaster>) findAll(criteria);
		return lstTestKeyMasters;
	}

	@Override
	public List<TestKeyValues> viewTestKeyValuesForApproval() {
		DetachedCriteria criteria = DetachedCriteria.forClass(TestKeyValues.class, "testKeyValues");
		
		criteria.add(Restrictions.eq("testKeyValues.isActive", Constants.U));
		criteria.addOrder(Order.asc("testKeyValues.testKeyValuesId"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<TestKeyValues> lstTestKeyValues = (List<TestKeyValues>) findAll(criteria);
		return lstTestKeyValues;
	}

	@Override
	public String checkTestKeyValuesApproveMultiUser(BigDecimal testKeyvaluePk,	String userName) {
		String approvalMsg="Fail";
		TestKeyValues testKeyMaster=(TestKeyValues) getSession().get(TestKeyValues.class, testKeyvaluePk);
		String approvalUser=testKeyMaster.getApprovedBy();
		if(approvalUser==null){
			testKeyMaster.setIsActive(Constants.Yes);
			testKeyMaster.setApprovedBy(userName);
			testKeyMaster.setApprovedDate(new Date());
			getSession().update(testKeyMaster);
			approvalMsg="Success";
		}else{
			approvalMsg="Fail";      
		}
		return approvalMsg;
	}

}
