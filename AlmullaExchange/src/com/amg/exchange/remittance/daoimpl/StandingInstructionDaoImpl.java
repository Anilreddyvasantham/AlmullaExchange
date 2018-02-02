package com.amg.exchange.remittance.daoimpl;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.model.StandingInstruction;
import com.amg.exchange.remittance.bean.StandingInstructionList;
import com.amg.exchange.remittance.dao.IStandingInstructionDao;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.TreasuryStandardInstruction;

@Repository
public class StandingInstructionDaoImpl<T> extends CommonDaoImpl implements IStandingInstructionDao<T>{

	@Override
	public void saveStandingInstruction(StandingInstruction standingInstrn) {
		// TODO Auto-generated method stub
		getSession().saveOrUpdate(standingInstrn);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StandingInstruction> standingInstrnDetailsByID(BigDecimal standingInstrnId) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(StandingInstruction.class, "standingInstruction");

		criteria.setFetchMode("standingInstruction.exCurrencyMasterByForeignCurrencyId", FetchMode.JOIN);
		criteria.createAlias("standingInstruction.exCurrencyMasterByForeignCurrencyId", "exCurrencyMasterByForeignCurrencyId",JoinType.INNER_JOIN);
		
		criteria.setFetchMode("standingInstruction.exCurrencyMasterByLocalTranxCurrencyId", FetchMode.JOIN);
		criteria.createAlias("standingInstruction.exCurrencyMasterByLocalTranxCurrencyId", "exCurrencyMasterByLocalTranxCurrencyId",JoinType.INNER_JOIN);
		
		criteria.setFetchMode("standingInstruction.exBankMaster", FetchMode.JOIN);
		criteria.createAlias("standingInstruction.exBankMaster", "exBankMaster",JoinType.INNER_JOIN);
		
		criteria.setFetchMode("standingInstruction.exBankBranchId", FetchMode.JOIN);
		criteria.createAlias("standingInstruction.exBankBranchId", "exBankBranchId",JoinType.INNER_JOIN);
		
		criteria.setFetchMode("standingInstruction.exbeneficaryMasterSeqId", FetchMode.JOIN);
		criteria.createAlias("standingInstruction.exbeneficaryMasterSeqId", "exbeneficaryMasterSeqId",JoinType.INNER_JOIN);
		
		criteria.setFetchMode("standingInstruction.exbeneficaryAccountSeqId", FetchMode.JOIN);
		criteria.createAlias("standingInstruction.exbeneficaryAccountSeqId", "exbeneficaryAccountSeqId",JoinType.INNER_JOIN);
		
		criteria.setFetchMode("standingInstruction.frequency", FetchMode.JOIN);
		criteria.createAlias("standingInstruction.frequency", "frequency",JoinType.INNER_JOIN);
		
		criteria.setFetchMode("standingInstruction.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("standingInstruction.fsCustomer", "fsCustomer",JoinType.INNER_JOIN);
		
		criteria.setFetchMode("standingInstruction.fsCountryMasterByBankCountryId", FetchMode.JOIN);
		criteria.createAlias("standingInstruction.fsCountryMasterByBankCountryId", "fsCountryMasterByBankCountryId",JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("standingInstruction.standingInstructionSeqId", standingInstrnId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		return (List<StandingInstruction>) findAll(criteria);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StandingInstruction> standingInstrnAllDetailsbyCustomer(BigDecimal customerId) {
		// TODO Auto-generated method stub
		
		DetachedCriteria criteria = DetachedCriteria.forClass(StandingInstruction.class, "standingInstruction");

		criteria.setFetchMode("standingInstruction.exCurrencyMasterByForeignCurrencyId", FetchMode.JOIN);
		criteria.createAlias("standingInstruction.exCurrencyMasterByForeignCurrencyId", "exCurrencyMasterByForeignCurrencyId",JoinType.INNER_JOIN);
		
		criteria.setFetchMode("standingInstruction.exCurrencyMasterByLocalTranxCurrencyId", FetchMode.JOIN);
		criteria.createAlias("standingInstruction.exCurrencyMasterByLocalTranxCurrencyId", "exCurrencyMasterByLocalTranxCurrencyId",JoinType.INNER_JOIN);
		
		criteria.setFetchMode("standingInstruction.exBankMaster", FetchMode.JOIN);
		criteria.createAlias("standingInstruction.exBankMaster", "exBankMaster",JoinType.INNER_JOIN);
		
		criteria.setFetchMode("standingInstruction.exBankBranchId", FetchMode.JOIN);
		criteria.createAlias("standingInstruction.exBankBranchId", "exBankBranchId",JoinType.INNER_JOIN);
		
		criteria.setFetchMode("standingInstruction.exbeneficaryMasterSeqId", FetchMode.JOIN);
		criteria.createAlias("standingInstruction.exbeneficaryMasterSeqId", "exbeneficaryMasterSeqId",JoinType.INNER_JOIN);
		
		criteria.setFetchMode("standingInstruction.exbeneficaryAccountSeqId", FetchMode.JOIN);
		criteria.createAlias("standingInstruction.exbeneficaryAccountSeqId", "exbeneficaryAccountSeqId",JoinType.INNER_JOIN);
		
		criteria.setFetchMode("standingInstruction.frequency", FetchMode.JOIN);
		criteria.createAlias("standingInstruction.frequency", "frequency",JoinType.INNER_JOIN);
		
		criteria.setFetchMode("standingInstruction.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("standingInstruction.fsCustomer", "fsCustomer",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCustomer.customerId", customerId));
		
		criteria.setFetchMode("standingInstruction.fsCountryMasterByBankCountryId", FetchMode.JOIN);
		criteria.createAlias("standingInstruction.fsCountryMasterByBankCountryId", "fsCountryMasterByBankCountryId",JoinType.INNER_JOIN);

		criteria.addOrder(Order.asc("standingInstruction.standingInstructionSeqId"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		return (List<StandingInstruction>) findAll(criteria);
	}

	@Override
	public List<TreasuryStandardInstruction> getAllRecordsFrom(BigDecimal treasuryDealHeaderId,BigDecimal dealNo) {
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(TreasuryStandardInstruction.class, "treasuryStandardInstruction");
		dCriteria.add(Restrictions.eq("treasuryStandardInstruction.ducumentNumber", dealNo));
		dCriteria.add(Restrictions.eq("treasuryStandardInstruction.treasuryDealDetail.treasuryDealDetailId",treasuryDealHeaderId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
 
		return (List<TreasuryStandardInstruction>) findAll(dCriteria);
	}

}
