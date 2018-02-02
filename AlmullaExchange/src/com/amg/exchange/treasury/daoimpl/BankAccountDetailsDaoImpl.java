package com.amg.exchange.treasury.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.model.BankAccountType;
import com.amg.exchange.common.model.BankAccountTypeDesc;
import com.amg.exchange.jvprocess.model.ViewGeneralValidationGlNo;
import com.amg.exchange.treasury.dao.IBankAccountDetailsDao;
import com.amg.exchange.treasury.model.AccountBalance;
import com.amg.exchange.treasury.model.BankAccount;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;

@SuppressWarnings("serial")
@Repository
public class BankAccountDetailsDaoImpl<T> extends CommonDaoImpl<T> implements
		IBankAccountDetailsDao<T>, Serializable {

	Logger log = Logger.getLogger(BankAccountDetailsDaoImpl.class);
	
	SessionStateManage sessionStateManage = new SessionStateManage();
	
	@SuppressWarnings("unchecked")
	@Override
	public void saveBankAccountDetails(BankAccountDetails bankaccdetails) {
		if(bankaccdetails != null && bankaccdetails.getBankAccountId() != null){
			if(bankaccdetails.getBankAccountId().getBankAccountId() != null){
				
				BankAccount accountdetails = (BankAccount) getSession().get(BankAccount.class, bankaccdetails.getBankAccountId().getBankAccountId());
				
				
				if(bankaccdetails.getRecordStatus().equals(Constants.U)){
					accountdetails.setApprovedBy(null);
					accountdetails.setApprovedDate(null);
					accountdetails.setModifier(sessionStateManage.getUserName());
					accountdetails.setUpdateDate(new Date());
					accountdetails.setRemarks(bankaccdetails.getRemarks());
					accountdetails.setRecordStatus(Constants.U);
				}else if(bankaccdetails.getRecordStatus().equals(Constants.D)){
					accountdetails.setApprovedBy(null);
					accountdetails.setApprovedDate(null);
					accountdetails.setModifier(sessionStateManage.getUserName());
					accountdetails.setUpdateDate(new Date());
					accountdetails.setRecordStatus(Constants.D);
					accountdetails.setRemarks(null);
				}else if(bankaccdetails.getRecordStatus().equals(Constants.Yes)){
					accountdetails.setApprovedBy(bankaccdetails.getApprovedBy());
					accountdetails.setApprovedDate(bankaccdetails.getApprovedDate());
					accountdetails.setRecordStatus(Constants.Yes);
					accountdetails.setRemarks(bankaccdetails.getRemarks());
				}else{
					accountdetails.setApprovedBy(null);
					accountdetails.setApprovedDate(null);
					accountdetails.setModifier(sessionStateManage.getUserName());
					accountdetails.setUpdateDate(new Date());
					accountdetails.setRemarks(bankaccdetails.getRemarks());
				}
				
				getSession().update(accountdetails);
			}
		}
		
		getSession().saveOrUpdate((T) bankaccdetails);
		
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<BankAccountDetails> getBankAccountDetails(BigDecimal bankId,String bankAcctNo) {

		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BankAccountDetails.class, "bankaccountdetails");

		detachedCriteria.setFetchMode("bankaccountdetails.fsCountryMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("bankaccountdetails.fsCountryMaster","fsCountryMaster", JoinType.INNER_JOIN);
		
		detachedCriteria.setFetchMode("bankaccountdetails.exBankMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("bankaccountdetails.exBankMaster","exBankMaster", JoinType.INNER_JOIN);
		detachedCriteria.add(Restrictions.eq("exBankMaster.bankId", bankId));

		detachedCriteria.setFetchMode("bankaccountdetails.fsCurrencyMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("bankaccountdetails.fsCurrencyMaster", "fsCurrencyMaster", JoinType.INNER_JOIN);
		
		detachedCriteria.add(Restrictions.eq("bankaccountdetails.bankAcctNo", bankAcctNo));
		
		detachedCriteria .setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<BankAccountDetails>) findAll(detachedCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveBankAccountDetailsId(AccountBalance accountBalance) {
		saveOrUpdate((T) accountBalance);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankMaster> getCurrencyOfBank(BigDecimal countryId,
			BigDecimal bankId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				BankMaster.class, "bankMaster");

		dCriteria.setFetchMode("bankMaster.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankMaster.fsCountryMaster", "fsCountryMaster",
				JoinType.INNER_JOIN);

		// Add FS_COUNTRY_MASTER Filter Condition
		// dCriteria.add(Restrictions.eq("fsCountryMaster.countryId",
		// countryId));

		dCriteria.setFetchMode("bankMaster.currencyMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankMaster.currencyMaster", "currencyMaster",
				JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("bankMaster.bankId", bankId));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<BankMaster>) findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankAccount> getBankAccountNoFromBankAccount(BigDecimal countryId, BigDecimal bankId) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BankAccount.class, "bankAccount");

		detachedCriteria.setFetchMode("bankAccount.fsCountryMaster",FetchMode.JOIN);
		detachedCriteria.createAlias("bankAccount.fsCountryMaster",	"fsCountryMaster", JoinType.INNER_JOIN);
		detachedCriteria.add(Restrictions.eq("fsCountryMaster.countryId",countryId));

		detachedCriteria.setFetchMode("bankAccount.exBankMaster",FetchMode.JOIN);
		detachedCriteria.createAlias("bankAccount.exBankMaster","exBankMaster", JoinType.INNER_JOIN);
		detachedCriteria.add(Restrictions.eq("exBankMaster.bankId", bankId));
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<BankAccount>) findAll(detachedCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankAccountTypeDesc> getAccountTypeList(BigDecimal languageId) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(
				BankAccountTypeDesc.class, "bankAccountTypeDesc");
				
		detachedCriteria.setFetchMode("bankAccountTypeDesc.languageId",FetchMode.JOIN);
		detachedCriteria.createAlias("bankAccountTypeDesc.languageId","languageId", JoinType.INNER_JOIN);
		
		detachedCriteria.setFetchMode("bankAccountTypeDesc.bankAccountTypeId", FetchMode.JOIN);
		detachedCriteria.createAlias("bankAccountTypeDesc.bankAccountTypeId", "bankAccountTypeId",JoinType.INNER_JOIN);
		 
		detachedCriteria.add(Restrictions.eq("languageId.languageId", languageId));
		detachedCriteria.add(Restrictions.eq("bankAccountTypeId.isActive",Constants.Yes));
		detachedCriteria.addOrder(Order.asc("bankAccountTypeDesc.bankAccountTypeDesc"));//ascending order applied
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY); 
		return (List<BankAccountTypeDesc>) findAll(detachedCriteria);
	}

	@Override
	public List<BankAccountDetails> getBankAccountDetails(BigDecimal countryId,	BigDecimal bankId, String accountNo) {
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BankAccountDetails.class, "bankAccountDetails");
		
		detachedCriteria.setFetchMode("bankAccountDetails.fsCountryMaster",FetchMode.JOIN);
		detachedCriteria.createAlias("bankAccountDetails.fsCountryMaster",	"fsCountryMaster", JoinType.INNER_JOIN);
		
		detachedCriteria.add(Restrictions.eq("fsCountryMaster.countryId",countryId));
		
		detachedCriteria.setFetchMode("bankAccountDetails.exBankMaster",FetchMode.JOIN);
		detachedCriteria.createAlias("bankAccountDetails.exBankMaster",	"exBankMaster", JoinType.INNER_JOIN);
		
		detachedCriteria.add(Restrictions.eq("exBankMaster.bankId",bankId));
		
		detachedCriteria.add(Restrictions.eq("bankAccountDetails.bankAcctNo",accountNo));
		
		
		
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY); 
		return (List<BankAccountDetails>) findAll(detachedCriteria);
	}

	@Override
	public List<AccountBalance> getAccountBalance(String accountNo,
			BigDecimal bankActDtlsID) {

		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AccountBalance.class, "accountBalance");
		
		detachedCriteria.setFetchMode("accountBalance.exBankAccountDetails",FetchMode.JOIN);
		detachedCriteria.createAlias("accountBalance.exBankAccountDetails",	"exBankAccountDetails", JoinType.INNER_JOIN);
		
		detachedCriteria.add(Restrictions.eq("exBankAccountDetails.bankAcctDetId",bankActDtlsID));
				
		detachedCriteria.add(Restrictions.eq("accountBalance.accountNo",accountNo));
		
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY); 
		return (List<AccountBalance>) findAll(detachedCriteria);
	}

	
	@Override
	public List<BankAccountDetails> getDataForView(BigDecimal countryId,BigDecimal bankId) {

		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BankAccountDetails.class, "bankAccountDetails");
		
		detachedCriteria.setFetchMode("bankAccountDetails.fsCountryMaster",FetchMode.JOIN);
		detachedCriteria.createAlias("bankAccountDetails.fsCountryMaster",	"fsCountryMaster", JoinType.INNER_JOIN);
		
		detachedCriteria.add(Restrictions.eq("fsCountryMaster.countryId",countryId));
		
		detachedCriteria.setFetchMode("bankAccountDetails.exBankMaster",FetchMode.JOIN);
		detachedCriteria.createAlias("bankAccountDetails.exBankMaster",	"exBankMaster", JoinType.INNER_JOIN);
		detachedCriteria.add(Restrictions.eq("exBankMaster.bankId",bankId));
		
		detachedCriteria.setFetchMode("bankAccountDetails.fsCurrencyMaster",FetchMode.JOIN);
		detachedCriteria.createAlias("bankAccountDetails.fsCurrencyMaster",	"fsCurrencyMaster", JoinType.INNER_JOIN);
		
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY); 
		return (List<BankAccountDetails>) findAll(detachedCriteria);
	}
	
	@Override
	public String getAccountTypeName(BigDecimal accountTypeId) {

		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BankAccountType.class, "bankAccountType");
		
		detachedCriteria.add(Restrictions.eq("bankAccountType.bankAccountTypeId",accountTypeId));
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY); 
		List<BankAccountType> accountTypeList=(List<BankAccountType>) findAll(detachedCriteria);
		return accountTypeList.get(0).getBankAccountTypeCode();
	}

	@Override
	public String approveBankAccDtls(HashMap<String, Object> saveMapInfo)
			throws Exception {
		String approveMsg;
		
		BigDecimal pkbankAccount= (BigDecimal) saveMapInfo.get("BankAccount");
		
		BigDecimal pkbankAccountDetails= (BigDecimal) saveMapInfo.get("BankAccountDetails");
		
		String userName= (String)saveMapInfo.get("UserName");
		
		try{
			
			BankAccount bankAccount=(BankAccount) getSession().get(BankAccount.class, pkbankAccount);
			
			BankAccountDetails bankAccountDetails=(BankAccountDetails) getSession().get(BankAccountDetails.class, pkbankAccountDetails);
			
			String bnkAccountAppBy=bankAccount.getApprovedBy();
			String bnlAccDtalAppBy=bankAccountDetails.getApprovedBy();
			
			if(bnkAccountAppBy==null && bnlAccDtalAppBy==null)
			{
				bankAccount.setRecordStatus(Constants.Yes);
				bankAccount.setApprovedBy(userName);
				bankAccount.setApprovedDate(new Date());
				bankAccount.setRemarks("");
				getSession().update(bankAccount);
				
				
				bankAccountDetails.setRecordStatus(Constants.Yes);
				bankAccountDetails.setApprovedBy(userName);
				bankAccountDetails.setApprovedDate(new Date());
				bankAccountDetails.setRemarks("");
				
				getSession().update(bankAccountDetails);
				
				
				approveMsg="Sucess";
				
				
			}else
			{
				approveMsg="Fail";
			}
		}catch(Exception e){
			log.info("Problem to redirect: " + e);
			throw e;
		}
		
		
		return approveMsg;
	}

	@Override
	public void saveBankAccountDetails(HashMap<String, Object> saveMapInfo)
			throws Exception {
		 
		BankAccount  bankAccount= (BankAccount) saveMapInfo.get("BankAccountSave");
		BankAccountDetails  bankAccountDetails= (BankAccountDetails) saveMapInfo.get("BankAccountDetails");
		//AccountBalance  accountBalance= (AccountBalance) saveMapInfo.get("AccountBalance");
		
		
		
		getSession().saveOrUpdate(bankAccount);
		
		//bankAccount
		BankAccount bankAcc = new BankAccount();
		bankAcc.setBankAccountId(bankAccount.getBankAccountId());
		bankAccountDetails.setBankAccountId(bankAcc);
		
		getSession().saveOrUpdate(bankAccountDetails);
		//getSession().saveOrUpdate(accountBalance);
	}

	@Override
	public List<ViewGeneralValidationGlNo> getAccountBalanceGLNo(BigDecimal companyCode, String glno) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(ViewGeneralValidationGlNo.class, "viewGeneralValidationGlNo");
		//dCriteria.add(Restrictions.eq("viewGeneralValidationGlNo.comcod", companyCode));
		dCriteria.add(Restrictions.eq("viewGeneralValidationGlNo.glno", glno));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<ViewGeneralValidationGlNo> lstViewGeneralValidationGlNo=  (List<ViewGeneralValidationGlNo>) findAll(dCriteria);
		
		return lstViewGeneralValidationGlNo;
	}
	
	@Override
	public List<ViewGeneralValidationGlNo> getAccountBalanceGLNoWithCurrency(String currencyCode, String glno) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(ViewGeneralValidationGlNo.class, "viewGeneralValidationGlNo");
		dCriteria.add(Restrictions.eq("viewGeneralValidationGlNo.curCod", currencyCode));
		dCriteria.add(Restrictions.eq("viewGeneralValidationGlNo.glno", glno));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<ViewGeneralValidationGlNo> lstViewGeneralValidationGlNo=  (List<ViewGeneralValidationGlNo>) findAll(dCriteria);
		
		return lstViewGeneralValidationGlNo;
	}

	@Override
	public HashMap<String, String> callPopulateBankAccountDeatils(
			HashMap<String, String> inputValues) throws AMGException {
		HashMap<String, String> outputValues = new HashMap<String, String>();
		Connection connection = null;
		String errString = null;
		
		try {
			connection = getDataSourceFromHibernateSession();
			CallableStatement cs;
			String call = " {call EX_P_POPULATE_BANK_ACCT_DTLS (?,?,?,?,? ) } ";
			cs = connection.prepareCall(call);
		 		
			cs.setBigDecimal(1, new BigDecimal(inputValues.get("BANK_ID")));
			cs.setString(2, inputValues.get("BANK_CODE"));
			cs.setBigDecimal(3, new BigDecimal(inputValues.get("BANK_ACCOUNT_ID")));
			cs.setBigDecimal(4, new BigDecimal(inputValues.get("BANK_ACCT_DET_ID")));
			cs.registerOutParameter(5, java.sql.Types.VARCHAR);
			cs.execute();
			errString = cs.getString(5);
 			outputValues.put("P_ERROR_MESSAGE", errString);
 			
		} catch (SQLException e) {
			String erromsg = "EX_P_POPULATE_BANK_BRANCH" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				String erromsg = "EX_P_POPULATE_BANK_BRANCH" + " : " + e.getMessage();
				throw new AMGException(erromsg);
			}
		}
		
		return outputValues;
 
	}

	@Override
	public HashMap<String, String> getAccountBalanceGLNoWithCurrencyWithProc(BigDecimal currencyId, String glno) throws AMGException {
		Connection connection = null;
		//String glDesc = null;
		HashMap<String, String> gldt = new HashMap<String, String>();
		
		try {
			connection = getDataSourceFromHibernateSession();
			CallableStatement cs;
			String call = " {call EX_CHK_GLNO (?,?,?,?) } ";
			cs = connection.prepareCall(call);
		 		
			cs.setString(1, glno);
			cs.setBigDecimal(2, currencyId);
			cs.registerOutParameter(3, java.sql.Types.VARCHAR);
			cs.registerOutParameter(4, java.sql.Types.VARCHAR);
			cs.execute();
			gldt.put("GL_DESC", cs.getString(3));
			gldt.put("ERR_MSG", cs.getString(4));
 			
		} catch (SQLException e) {
			String erromsg = "EX_CHK_GLNO" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} catch (Exception e) {
			String erromsg = "EX_CHK_GLNO" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				String erromsg = "EX_CHK_GLNO" + " : " + e.getMessage();
				throw new AMGException(erromsg);
			}
		}
		
		return gldt;
	}
}
