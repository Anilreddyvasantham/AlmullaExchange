package com.amg.exchange.remittance.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.registration.daoimpl.CompanyMasterDaoImpl;
import com.amg.exchange.registration.model.RoleMaster;
import com.amg.exchange.remittance.dao.ICashLimitDao;
import com.amg.exchange.remittance.model.CashLimit;
import com.amg.exchange.remittance.model.CashLimitType;

@SuppressWarnings("unchecked")
@Repository
public class CashLimitDaoImpl<T> extends CommonDaoImpl<T> implements ICashLimitDao, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(CompanyMasterDaoImpl.class);

	@Override
	public void save(CashLimit cashLimit) {
		LOGGER.info("Entering into save method");
		try {
			getSession().saveOrUpdate(cashLimit);
		} catch (Exception e) {
			System.out.println(e);
		}
		LOGGER.info("Exit into save method");
	}

	@Override
	public List<CashLimitType> getCashLimitType() {
		LOGGER.info("Entering into viewMasterRecords method");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CashLimitType.class, "cashLimitType");
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exit into viewMasterRecords method");
		return (List<CashLimitType>) findAll(detachedCriteria);
	}

	@Override
	public List<CashLimit> populateDetails(BigDecimal countryId, BigDecimal limitId) {
		LOGGER.info("*********************countryId  " + countryId + " limitId " + limitId);
		DetachedCriteria dcriteria = DetachedCriteria.forClass(CashLimit.class, "cashLimit");
		dcriteria.setFetchMode("cashLimit.limitType", FetchMode.JOIN);
		dcriteria.createAlias("cashLimit.limitType", "limit", JoinType.INNER_JOIN);
		dcriteria.add(Restrictions.eq("limit.limitTypeId", limitId));
		dcriteria.setFetchMode("cashLimit.country", FetchMode.JOIN);
		dcriteria.createAlias("cashLimit.country", "countryMaster", JoinType.INNER_JOIN);
		dcriteria.add(Restrictions.eq("countryMaster.countryId", countryId));
		dcriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CashLimit> list = (List<CashLimit>) findAll(dcriteria);
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	@Override
	public List<CashLimit> viewbasedOnCountry(BigDecimal countryId) {
		DetachedCriteria dcriteria = DetachedCriteria.forClass(CashLimit.class, "cashLimit");
		
		dcriteria.setFetchMode("cashLimit.limitType", FetchMode.JOIN);
		dcriteria.createAlias("cashLimit.limitType", "limitType", JoinType.INNER_JOIN);
		
		dcriteria.setFetchMode("cashLimit.country", FetchMode.JOIN);
		dcriteria.createAlias("cashLimit.country", "country", JoinType.INNER_JOIN);
		dcriteria.add(Restrictions.eq("country.countryId", countryId));
		
		dcriteria.addOrder(Order.asc("limitType.limitTypeId"));
		dcriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CashLimit> list = (List<CashLimit>) findAll(dcriteria);
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	@Override
	public List<RoleMaster> getRoleMasterList() {
		DetachedCriteria dcriteria = DetachedCriteria.forClass(RoleMaster.class, "roleMaster");
		dcriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<RoleMaster>) findAll(dcriteria);
	}
}
