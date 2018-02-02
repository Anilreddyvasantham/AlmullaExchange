package com.amg.exchange.treasury.daoimpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.remittance.model.CashRate;
import com.amg.exchange.remittance.model.UnApprovalCashRate;
import com.amg.exchange.treasury.dao.ICashRateDao;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.TreasuryDealHeader;

@SuppressWarnings("rawtypes")
@Repository
public class CashRateDaoImpl extends CommonDaoImpl implements ICashRateDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<CashRate> getCashRateRecordsFromDB(BigDecimal countryBranchId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(CashRate.class, "cashRate");

		criteria.setFetchMode("cashRate.countryBranchId", FetchMode.JOIN);
		criteria.createAlias("cashRate.countryBranchId", "countryBranchId", JoinType.INNER_JOIN);

		criteria.setFetchMode("cashRate.appCountryId", FetchMode.JOIN);
		criteria.createAlias("cashRate.appCountryId", "appCountryId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("cashRate.countryBranchId.countryBranchId", countryBranchId));
		
		criteria.addOrder(Order.asc("cashRate.alternateCurrencyCode"));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<CashRate>) findAll(criteria);
	}

	@Override
	public void save(UnApprovalCashRate unApprovalCashRate) {

		getSession().saveOrUpdate(unApprovalCashRate);
	}

	@Override
	public void cashRateSave(CashRate cashRate) {

		getSession().saveOrUpdate(cashRate);
		
	}

	@Override
	public void delete(BigDecimal unApprovalCashRateId) {
		UnApprovalCashRate unApprovalCashRate = (UnApprovalCashRate) getSession().get(UnApprovalCashRate.class, unApprovalCashRateId);
		getSession().delete(unApprovalCashRate);
		getSession().flush();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UnApprovalCashRate> getCashRateForApprove(String isActive) {

		DetachedCriteria criteria = DetachedCriteria.forClass(UnApprovalCashRate.class, "unApprovalCashRate");

		/*criteria.setFetchMode("unApprovalCashRate.countryBranchId", FetchMode.JOIN);
		criteria.createAlias("unApprovalCashRate.countryBranchId", "countryBranchId", JoinType.INNER_JOIN);*/

		criteria.setFetchMode("unApprovalCashRate.appCountryId", FetchMode.JOIN);
		criteria.createAlias("unApprovalCashRate.appCountryId", "appCountryId", JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("unApprovalCashRate.isActive", isActive));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<UnApprovalCashRate>) findAll(criteria);
	}

	@Override
	public String getQuoteName(BigDecimal currencyId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CurrencyMaster.class, "currencyMaster");
		criteria.add(Restrictions.eq("currencyMaster.currencyId", currencyId));
		List<CurrencyMaster> data = (List<CurrencyMaster>) findAll(criteria);
		return data.get(0).getQuoteName();

	}
	
	@Override
	public String getCurrencyCode(BigDecimal currencyId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CurrencyMaster.class, "currencyMaster");
		criteria.add(Restrictions.eq("currencyMaster.currencyId", currencyId));
		List<CurrencyMaster> data = (List<CurrencyMaster>) findAll(criteria);
		return data.get(0).getCurrencyCode();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CurrencyMaster> getCurrencyList(BigDecimal localCurrencyId) {
		// LOG.info("Entering into getCurrencyList method");
		DetachedCriteria criteria = DetachedCriteria.forClass(CurrencyMaster.class, "currencyMaster");
		criteria.add(Restrictions.ne("currencyMaster.currencyId", localCurrencyId));
		criteria.addOrder(Order.asc("currencyMaster.currencyName"));
		// LOG.info("Exit into getCurrencyList method");
		return (List<CurrencyMaster>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BigDecimal getBranchDetails(BigDecimal countryBranchId) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(CountryBranch.class, "countryBranch");
		dCriteria.setFetchMode("countryBranch.countryMaster", FetchMode.JOIN);
		dCriteria.createAlias("countryBranch.countryMaster", "countryMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("countryBranch.countryBranchId", countryBranchId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CountryBranch> data = (List<CountryBranch>) findAll(dCriteria);

		if (data.isEmpty())
			return null;

		return data.get(0).getBranchId();
		// return (List<CountryBranch>) findAll(dCriteria);
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UnApprovalCashRate> getAllUnAppCashRate() {

		DetachedCriteria criteria = DetachedCriteria.forClass(UnApprovalCashRate.class, "unApprovalCashRate");

		/*criteria.setFetchMode("unApprovalCashRate.countryBranchId", FetchMode.JOIN);
		criteria.createAlias("unApprovalCashRate.countryBranchId", "countryBranchId", JoinType.INNER_JOIN);*/

		criteria.setFetchMode("unApprovalCashRate.baseCurrencyId", FetchMode.JOIN);
		criteria.createAlias("unApprovalCashRate.baseCurrencyId", "baseCurrencyId", JoinType.INNER_JOIN);
		
		criteria.setFetchMode("unApprovalCashRate.alternateCurrencyId", FetchMode.JOIN);
		criteria.createAlias("unApprovalCashRate.alternateCurrencyId", "alternateCurrencyId", JoinType.INNER_JOIN);


		criteria.setFetchMode("unApprovalCashRate.appCountryId", FetchMode.JOIN);
		criteria.createAlias("unApprovalCashRate.appCountryId", "appCountryId", JoinType.INNER_JOIN);

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<UnApprovalCashRate>) findAll(criteria);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CashRate> isExistAllUnAppCashRate(BigDecimal alternateCurrencyId,BigDecimal baseCurrencyid,BigDecimal appliationCountryId, BigDecimal branchId) {
		
		
		System.out.println("isExistAllUnAppCashRate ================= >alternateCurrencyId "+alternateCurrencyId+" ============ branchId "+branchId);

		DetachedCriteria criteria = DetachedCriteria.forClass(CashRate.class, "cashRate");
		List<CashRate> data=new ArrayList<CashRate>();
		if(branchId!=null)
		{
			criteria.setFetchMode("cashRate.countryBranchId", FetchMode.JOIN);
			criteria.createAlias("cashRate.countryBranchId", "countryBranchId", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("countryBranchId.countryBranchId", branchId));
			
			
			criteria.setFetchMode("cashRate.alternateCurrencyId", FetchMode.JOIN);
			criteria.createAlias("cashRate.alternateCurrencyId", "alternateCurrencyId", JoinType.INNER_JOIN);
			
			criteria.add(Restrictions.eq("alternateCurrencyId.currencyId", alternateCurrencyId));

			criteria.setFetchMode("cashRate.baseCurrencyId", FetchMode.JOIN);
			criteria.createAlias("cashRate.baseCurrencyId", "baseCurrencyId", JoinType.INNER_JOIN);
			
			criteria.add(Restrictions.eq("baseCurrencyId.currencyId", baseCurrencyid));
			
			criteria.setFetchMode("cashRate.appCountryId", FetchMode.JOIN);
			criteria.createAlias("cashRate.appCountryId", "appCountryId", JoinType.INNER_JOIN);
			
			criteria.add(Restrictions.eq("appCountryId.countryId", appliationCountryId));

			criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			
			data = (List<CashRate>) findAll(criteria);
			
		}else
		{
			data=getCashRateWithoutBranch(alternateCurrencyId, baseCurrencyid, appliationCountryId, branchId);
		}
		
		
		

		return data;

		
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CountryBranch> getCountryBranchList(BigDecimal countryBranchId) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(CountryBranch.class, "countryBranch");
		dCriteria.setFetchMode("countryBranch.countryMaster", FetchMode.JOIN);
		dCriteria.createAlias("countryBranch.countryMaster", "countryMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("countryBranch.countryBranchId", countryBranchId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CountryBranch> data = (List<CountryBranch>) findAll(dCriteria);

		if (data.isEmpty())
			return null;

		return data;
		// return (List<CountryBranch>) findAll(dCriteria);
	}
	
	public List<CashRate> getCashRateWithoutBranch(BigDecimal alternateCurrencyId,BigDecimal baseCurrencyid,BigDecimal appliationCountryId, BigDecimal branchId)
	{
		String hql = "from CashRate as cashRate where cashRate.alternateCurrencyId.currencyId = :alternateCurrencyId1  and cashRate.baseCurrencyId.currencyId = :baseCurrencyId1 and cashRate.appCountryId.countryId= :appCountryId and cashRate.countryBranchId.countryBranchId is null ";
		Query query = getSession().createQuery(hql); 
		query.setParameter("alternateCurrencyId1", alternateCurrencyId);
		query.setParameter("baseCurrencyId1", baseCurrencyid); 
		query.setParameter("appCountryId", appliationCountryId); 
		List<CashRate> lstCashRate=query.list();
		
		return lstCashRate;
	}
	
	public List<UnApprovalCashRate> getUnApprovalCashRateWithoutBranch(BigDecimal alternateCurrencyId,BigDecimal baseCurrencyid,BigDecimal appliationCountryId, BigDecimal branchId)
	{
		String hql = "from UnApprovalCashRate as cashRate where cashRate.alternateCurrencyId.currencyId = :alternateCurrencyId1  and cashRate.baseCurrencyId.currencyId = :baseCurrencyId1 and cashRate.appCountryId.countryId= :appCountryId and cashRate.countryBranchId.countryBranchId =:countryBranchId1";
		Query query = getSession().createQuery(hql); 
		query.setParameter("alternateCurrencyId1", alternateCurrencyId);
		query.setParameter("baseCurrencyId1", baseCurrencyid); 
		query.setParameter("appCountryId", appliationCountryId); 
		query.setParameter("countryBranchId1", branchId );
		List<UnApprovalCashRate> lstCashRate=query.list();
		
		return lstCashRate;
	}
	

}
