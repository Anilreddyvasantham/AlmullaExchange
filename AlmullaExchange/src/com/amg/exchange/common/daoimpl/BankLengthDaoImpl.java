package com.amg.exchange.common.daoimpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.dao.IBankLengthDao;
import com.amg.exchange.loyalty.model.LoyaltyMasterEncashment;
import com.amg.exchange.loyalty.model.LoyaltyParameterSetting;
import com.amg.exchange.treasury.model.BankAccountLength;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.util.Constants;

@Repository
public class BankLengthDaoImpl<T> extends CommonDaoImpl<T> implements IBankLengthDao{

	@SuppressWarnings("unchecked")
	@Override
	public void saveBankLengthRecord(BankAccountLength bankLength) {
		saveOrUpdate((T) bankLength);
	}

	/*public void update()
	{

		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				BankAccountDetails.class, "bankAccountDetails");

		dCriteria.setFetchMode("bankAccountDetails.exBankMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankAccountDetails.exBankMaster", "exBankMaster",JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("exBankMaster.bankId", bankId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
	}
*/
	@SuppressWarnings("unchecked")
	@Override
	public List<BankAccountLength> findBankId(BigDecimal bankId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankAccountLength.class, "bankLength");

		dCriteria.setFetchMode("bankLength.bankMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankLength.bankMaster", "bankMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("bankMaster.bankId", bankId));

		//dCriteria.add(Restrictions.eq("bankLength.recordStatus", Constants.U));
		dCriteria.add(Restrictions.disjunction().add( Restrictions.eq("bankLength.recordStatus", Constants.U)).add(Restrictions.eq("bankLength.recordStatus", Constants.D )).add(Restrictions.eq("bankLength.recordStatus", Constants.Yes )));
		//dCriteria.add(Restrictions.disjunction().add( Restrictions.eq("bankLength.recordStatus", Constants.U)).add(Restrictions.eq("bankLength.recordStatus", Constants.Yes )));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<BankAccountLength>) findAll(dCriteria);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BankAccountLength> checkBankLength(BigDecimal bankId, BigDecimal bankLength) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankAccountLength.class, "bankLength");

		dCriteria.setFetchMode("bankLength.bankMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankLength.bankMaster", "bankMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("bankMaster.bankId", bankId));

		dCriteria.add(Restrictions.eq("bankLength.acLength", bankLength));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<BankAccountLength>) findAll(dCriteria);
	}

	@Override
	public void deleteBankLengthRecord(BigDecimal bankLengthId, String username, Boolean delete) {
		// TODO Auto-generated method stub
		BankAccountLength bankAccountLength = (BankAccountLength) getSession().get(BankAccountLength.class, bankLengthId);
		if (delete) {
			bankAccountLength.setRecordStatus(Constants.D);
		} else {
			bankAccountLength.setRecordStatus(Constants.U);
		}

		// bankAccountLength.setApprovedBy(username);
		// addtionalBankRule.setApprovedDate(new Date());
		getSession().update(bankAccountLength);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BankAccountLength> findAllLengthByBankId(BigDecimal bankId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankAccountLength.class, "bankLength");

		dCriteria.setFetchMode("bankLength.bankMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankLength.bankMaster", "bankMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("bankMaster.bankId", bankId));

		// dCriteria.add(Restrictions.eq("bankLength.recordStatus",
		// Constants.U));
		dCriteria.add(Restrictions.disjunction().add(Restrictions.eq("bankLength.recordStatus", Constants.U)).add(Restrictions.eq("bankLength.recordStatus", Constants.D)).add(Restrictions.eq("bankLength.recordStatus", Constants.Yes)));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<BankAccountLength>) findAll(dCriteria);
	}

	/*koti start activate and deactivate service bank length ############ 17062016*/
	@Override
	public void DeActiveRecordToPendingForApprovalOfBankLength(BigDecimal bankLengthId, String userName) {
		BankAccountLength bankAccountLength = (BankAccountLength) getSession().get(BankAccountLength.class, bankLengthId);
		/*bankAccountLength.setModifiedBy(userName);
		bankAccountLength.setModifiedDate(new Date());
		bankAccountLength.setApprovedBy(null);
		bankAccountLength.setApprovedDate(null);*/
		bankAccountLength.setRemarks(null);
		bankAccountLength.setRecordStatus(Constants.U);
	    getSession().update(bankAccountLength);
	}
	
	 @Override
	  public void upDateActiveRecordtoDeActive(BigDecimal bankLengthId, String remarks, String userName) {
		 BankAccountLength bankAccountLength = (BankAccountLength) getSession().get(BankAccountLength.class, bankLengthId);
		 bankAccountLength.setRemarks(remarks);
		 /*bankAccountLength.setModifiedBy(userName);
		 bankAccountLength.setModifiedDate(new Date());
		 bankAccountLength.setApprovedBy(null);
		 bankAccountLength.setApprovedDate(null);*/
		 bankAccountLength.setRecordStatus(Constants.D);
		    getSession().update(bankAccountLength);
	  }

	@Override
	public void DeActiveRecordToPendingForApprovalOfBankMaster(BigDecimal bankIdInternal, String userName) {
		BankMaster bankMaster = (BankMaster) getSession().get(BankMaster.class, bankIdInternal);
		bankMaster.setModifier(userName);
		bankMaster.setUpdateDate(new Date());
		bankMaster.setApprovedBy(null);
		bankMaster.setApprovedDate(null);
		bankMaster.setRemarks(null);
		bankMaster.setRecordStatus(Constants.U);
	    getSession().update(bankMaster);
	}

	@Override
	public void upDateActiveRecordtoDeActiveBankMaster(BigDecimal bankIdInternal, String userName) {
		BankMaster bankMaster = (BankMaster) getSession().get(BankMaster.class, bankIdInternal);
		//bankMaster.setRemarks(remarks);
		bankMaster.setModifier(userName);
		bankMaster.setUpdateDate(new Date());
		bankMaster.setApprovedBy(null);
		bankMaster.setApprovedDate(null);
		bankMaster.setRecordStatus(Constants.U);
	    getSession().update(bankMaster);
	}

	@Override
	public List<BankAccountLength> findBankIdApprovalBankLength(BigDecimal bankIdInternal) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankAccountLength.class, "bankLength");

		dCriteria.setFetchMode("bankLength.bankMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankLength.bankMaster", "bankMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("bankMaster.bankId", bankIdInternal));

		dCriteria.add(Restrictions.eq("bankLength.recordStatus", Constants.U));
		//dCriteria.add(Restrictions.disjunction().add( Restrictions.eq("bankLength.recordStatus", Constants.U)).add(Restrictions.eq("bankLength.recordStatus", Constants.D )).add(Restrictions.eq("bankLength.recordStatus", Constants.Yes )));
		//dCriteria.add(Restrictions.disjunction().add( Restrictions.eq("bankLength.recordStatus", Constants.U)).add(Restrictions.eq("bankLength.recordStatus", Constants.Yes )));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BankAccountLength> lstBankAccountLength=(List<BankAccountLength>) findAll(dCriteria);
		return lstBankAccountLength;
	}
	/*koti End activate and deactivate service bank length ############ 17062016*/

	@Override
	public void deleteRecordBankAccountLength(BigDecimal bankLengthId) {
		BankAccountLength bankAccountLength = (BankAccountLength) getSession().get(BankAccountLength.class, bankLengthId);
		    getSession().delete(bankAccountLength);
	}
}
