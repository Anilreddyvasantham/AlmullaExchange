package com.amg.exchange.treasury.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.treasury.dao.IBankAccountDao;
import com.amg.exchange.treasury.model.BankAccount;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.ServiceMaster;
import com.amg.exchange.util.Constants;

@SuppressWarnings("serial")
@Repository
public class BankAccountDaoImpl<T> extends CommonDaoImpl<T> implements
		IBankAccountDao<T>, Serializable {

	@SuppressWarnings("unchecked")
	@Override
	public void saveBankAccount(BankAccount bankaccount) {
		saveOrUpdate((T) bankaccount);

	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<BankBranch> getBankBranchList(BigDecimal bankId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				BankBranch.class, "bankBranch");

		dCriteria.setFetchMode("bankBranch.exBankMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankBranch.exBankMaster", "exBankMaster",
				JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("exBankMaster.bankId", bankId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<BankBranch>) findAll(dCriteria);
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
	public List<BankAccount> getDuplicateAccountNo(BigDecimal bankId,String bankAccountNo) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankAccount.class, "bankAccount");

		dCriteria.setFetchMode("bankAccount.exBankMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankAccount.exBankMaster", "exBankMaster",JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exBankMaster.bankId", bankId));
		dCriteria.add(Restrictions.eq("bankAccount.debitAcct", bankAccountNo));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<BankAccount> data = (List<BankAccount>) findAll(dCriteria);

		return data;

	}

	@SuppressWarnings("unchecked")
	@Override
	public void approveBankAccount(BigDecimal bankAccPk,String userName) {
		// TODO Auto-generated method stub
		BankAccount servicebank=(BankAccount) getSession().get(BankAccount.class, bankAccPk);
		servicebank.setRecordStatus(Constants.Yes);
		servicebank.setApprovedBy(userName);
		servicebank.setApprovedDate(new Date());
		getSession().update(servicebank);
		
	}

	@Override
	public void procErrorToUnApprove(BigDecimal bankAccountDetId,BigDecimal fetchAccHeaderId) {
		
		BankAccount servicebank=(BankAccount) getSession().get(BankAccount.class, fetchAccHeaderId);
		servicebank.setRecordStatus(Constants.U);
		servicebank.setApprovedBy(null);
		servicebank.setApprovedDate(null);
		getSession().saveOrUpdate(servicebank);
		
		BankAccountDetails bankAccountDetails=(BankAccountDetails) getSession().get(BankAccountDetails.class, bankAccountDetId); 
		bankAccountDetails.setRecordStatus(Constants.U);
		bankAccountDetails.setApprovedBy(null);
		bankAccountDetails.setApprovedDate(null);
		getSession().saveOrUpdate(bankAccountDetails);
		
	}
}
