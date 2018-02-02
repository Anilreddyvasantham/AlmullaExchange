package com.amg.exchange.treasury.daoimpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.foreigncurrency.model.CurrencyWiseDenomination;
import com.amg.exchange.treasury.dao.ICurrencyDenominationDao;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.util.Constants;
@Repository
@SuppressWarnings("unchecked")
public class CurrencyDenominationDaoimpl<T> extends CommonDaoImpl<T> implements ICurrencyDenominationDao{

	
	@Override
	public List<CurrencyWiseDenomination> getAllListToDb(BigDecimal countryId,BigDecimal currencyId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CurrencyWiseDenomination.class, "currencyWiseDenomination");
		criteria.setFetchMode("currencyWiseDenomination.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("currencyWiseDenomination.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		criteria.setFetchMode("currencyWiseDenomination.exCurrencyMaster", FetchMode.JOIN);
		criteria.createAlias("currencyWiseDenomination.exCurrencyMaster", "exCurrencyMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("exCurrencyMaster.currencyId", currencyId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<CurrencyWiseDenomination>) findAll(criteria);

	}

	@Override
	public List<CurrencyWiseDenomination> getAllValidateBean(BigDecimal countryId, BigDecimal currencyId, BigDecimal denomonationAmount) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CurrencyWiseDenomination.class, "currencyWiseDenomination");
		criteria.setFetchMode("currencyWiseDenomination.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("currencyWiseDenomination.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		criteria.setFetchMode("currencyWiseDenomination.exCurrencyMaster", FetchMode.JOIN);
		criteria.createAlias("currencyWiseDenomination.exCurrencyMaster", "exCurrencyMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("exCurrencyMaster.currencyId", currencyId));
		criteria.add(Restrictions.eq("currencyWiseDenomination.denominationAmount", denomonationAmount));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<CurrencyWiseDenomination>) findAll(criteria);
	}

	@Override
	public void save(CurrencyWiseDenomination currencyWiseDenomination) {
		getSession().saveOrUpdate(currencyWiseDenomination);
	}

	@Override
	public void deleteRecordPermanetly(BigDecimal denominationid) {
		CurrencyWiseDenomination currencyWiseDenomination=(CurrencyWiseDenomination) getSession().get(CurrencyWiseDenomination.class, denominationid);
		getSession().delete(currencyWiseDenomination);
	}

	@Override
	public List<CurrencyWiseDenomination> getAllRecordsTofetchDB() {
		DetachedCriteria criteria = DetachedCriteria.forClass(CurrencyWiseDenomination.class, "currencyWiseDenomination");
		criteria.add(Restrictions.eq("currencyWiseDenomination.isActive", Constants.U));
		return (List<CurrencyWiseDenomination>) findAll(criteria);
	}

	@Override
	public String CountryNameList(BigDecimal countryId, BigDecimal languageId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CountryMasterDesc.class, "countryMasterDesc");
		dCriteria.setFetchMode("countryMasterDesc.fsCountryMaster",FetchMode.JOIN);
		dCriteria.createAlias("countryMasterDesc.fsCountryMaster","fsCountryMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		dCriteria.add(Restrictions.eq("countryMasterDesc.fsLanguageType.languageId", languageId));
		return ((CountryMasterDesc) dCriteria.getExecutableCriteria(getSession()).list().get(0)).getCountryName();
	}

	@Override
	public void approveRecord(BigDecimal denominationid, String userName) {
		CurrencyWiseDenomination currencyWiseDenomination=(CurrencyWiseDenomination) getSession().get(CurrencyWiseDenomination.class, denominationid);
		currencyWiseDenomination.setIsActive(Constants.Yes);
		currencyWiseDenomination.setApprovedBy(userName);
		currencyWiseDenomination.setApprovedDate(new Date());
		getSession().update(currencyWiseDenomination);
	}

	@Override
	public List<CurrencyMaster> getBasedOnCountyCurrency(BigDecimal countryId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CurrencyMaster.class, "currencyMaster");
		dCriteria.setFetchMode("currencyMaster.fsCountryMaster",FetchMode.JOIN);
		dCriteria.createAlias("currencyMaster.fsCountryMaster","fsCountryMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<CurrencyMaster>) findAll(dCriteria);
	}

	@Override
	public String getAllDenominationDesc(BigDecimal countryId,BigDecimal currencyId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CurrencyMaster.class, "currencyMaster");
		criteria.setFetchMode("currencyMaster.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("currencyMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		criteria.add(Restrictions.eq("currencyMaster.currencyId", currencyId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return ((CurrencyMaster) criteria.getExecutableCriteria(getSession()).list().get(0)).getQuoteName();
	}

	@Override
	public String getAllDenominationcurrencyDesc(BigDecimal countryId, BigDecimal currencyId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CurrencyMaster.class, "currencyMaster");
		criteria.setFetchMode("currencyMaster.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("currencyMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		criteria.add(Restrictions.eq("currencyMaster.currencyId", currencyId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return ((CurrencyMaster) criteria.getExecutableCriteria(getSession()).list().get(0)).getDecimalName();
	}

	@Override
	public List<CurrencyWiseDenomination> getAllRecordsToApproveFromDb(BigDecimal countryId, BigDecimal currencyId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CurrencyWiseDenomination.class, "currencyWiseDenomination");
		criteria.setFetchMode("currencyWiseDenomination.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("currencyWiseDenomination.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		criteria.setFetchMode("currencyWiseDenomination.exCurrencyMaster", FetchMode.JOIN);
		criteria.createAlias("currencyWiseDenomination.exCurrencyMaster", "exCurrencyMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("exCurrencyMaster.currencyId", currencyId));
		criteria.add(Restrictions.eq("currencyWiseDenomination.isActive", Constants.U));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<CurrencyWiseDenomination>) findAll(criteria);
	}

	@Override
	public void saveRecorsForApproved(CurrencyWiseDenomination currencyWiseDenomination) {
		getSession().saveOrUpdate(currencyWiseDenomination);
		
	}

	@Override
	public void updateApproveRecord(List<BigDecimal> lstApproved,String userName) {
		for (BigDecimal bigDecimal : lstApproved) {
		CurrencyWiseDenomination currencyWiseDenomination= (CurrencyWiseDenomination) getSession().get(CurrencyWiseDenomination.class, bigDecimal);
		currencyWiseDenomination.setApprovedBy(userName);
		currencyWiseDenomination.setApprovedDate(new Date());
		currencyWiseDenomination.setIsActive(Constants.Yes);
		getSession().update(currencyWiseDenomination);
		}
	}

	@Override
	public String approvalAllRecord(List<BigDecimal> lstApproved,String userName) {
		String list = null;
		for (BigDecimal bigDecimal : lstApproved) {
			CurrencyWiseDenomination currencyWiseDenomination= (CurrencyWiseDenomination) getSession().get(CurrencyWiseDenomination.class, bigDecimal);
			String approvedUser=currencyWiseDenomination.getApprovedBy();
			if(approvedUser==null)
			{
				currencyWiseDenomination.setApprovedBy(userName);
				currencyWiseDenomination.setApprovedDate(new Date());
				currencyWiseDenomination.setIsActive(Constants.Yes);
			getSession().update(currencyWiseDenomination);
			list="Success";
			}else{
				list="Fail";
			}
		}
		return list;
	}

}
