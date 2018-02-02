package com.amg.exchange.remittance.daoimpl;

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
import com.amg.exchange.model.OnlineRateAlert;
import com.amg.exchange.model.RateAlertFrequency;
import com.amg.exchange.remittance.dao.IRateAlertSetupDao;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.ExchangeRate;
import com.amg.exchange.util.Constants;

/**
 * @author Mohan
 * 
 * @param <T>
 */
@Repository
public class RateAlertSetupDaoImpl<T> extends CommonDaoImpl<T> implements IRateAlertSetupDao {

	private static final Logger logger = Logger.getLogger(RateAlertSetupDaoImpl.class);

	@Override
	public BigDecimal getExchangeRatefortheCountry(BigDecimal appcountryId, BigDecimal currencyId) {

		logger.debug("applicationCountryId " + appcountryId);
		logger.debug("currencyId " + currencyId);
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ExchangeRate.class, "exchangeRate");

		detachedCriteria.setFetchMode("exchangeRate.applicationCountryId", FetchMode.SELECT);
		detachedCriteria.createAlias("exchangeRate.applicationCountryId", "applicationCountryId", JoinType.INNER_JOIN);
		detachedCriteria.add(Restrictions.eq("applicationCountryId.countryId", appcountryId));
		detachedCriteria.setFetchMode("exchangeRate.currencyId", FetchMode.SELECT);
		detachedCriteria.createAlias("exchangeRate.currencyId", "currencyId", JoinType.INNER_JOIN);
		detachedCriteria.add(Restrictions.eq("currencyId.currencyId", currencyId));
		logger.debug(((ExchangeRate) detachedCriteria.getExecutableCriteria(getSession()).list().get(0)).getSellrateMin());
		logger.debug(((ExchangeRate) detachedCriteria.getExecutableCriteria(getSession()).list().get(0)).getSellrateMin());

		return (((ExchangeRate) detachedCriteria.getExecutableCriteria(getSession()).list().get(0)).getSellrateMin());

		// return
		// ((List<ExchangeRate>)findAll(detachedCriteria)).get(0).getSellrateMin();
	}

	@Override
	public void save(OnlineRateAlert onlineRateAlert) {
		getSession().save(onlineRateAlert);

	}

	@Override
	public List<RateAlertFrequency> getHowOftenList() {

		DetachedCriteria criteria = DetachedCriteria.forClass(RateAlertFrequency.class, "rateAlertFrequency");

		criteria.addOrder(Order.asc("rateAlertFrequency.onlineRateAlertId"));
		return (List<RateAlertFrequency>) findAll(criteria);
	}

	@Override
	public List<CurrencyMaster> getCurrencyList() {
		DetachedCriteria criteria = DetachedCriteria.forClass(CurrencyMaster.class, "currencyMaster");
		criteria.addOrder(Order.asc("currencyMaster.currencyName"));
		criteria.add(Restrictions.eq("currencyMaster.isactive", Constants.Yes));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<CurrencyMaster>) findAll(criteria);
	}

	@Override
	public ExchangeRate getExchangeRate(BigDecimal appcountryId, BigDecimal currencyId) {

		logger.debug("applicationCountryId " + appcountryId);
		logger.debug("currencyId " + currencyId);
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ExchangeRate.class, "exchangeRate");

		detachedCriteria.setFetchMode("exchangeRate.applicationCountryId", FetchMode.SELECT);
		detachedCriteria.createAlias("exchangeRate.applicationCountryId", "applicationCountryId", JoinType.INNER_JOIN);
		detachedCriteria.add(Restrictions.eq("applicationCountryId.countryId", appcountryId));
		detachedCriteria.setFetchMode("exchangeRate.currencyId", FetchMode.SELECT);
		detachedCriteria.createAlias("exchangeRate.currencyId", "currencyId", JoinType.INNER_JOIN);
		detachedCriteria.add(Restrictions.eq("currencyId.currencyId", currencyId));
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		logger.debug(((ExchangeRate) detachedCriteria.getExecutableCriteria(getSession()).list().get(0)).getSellrateMin());
		logger.debug(((ExchangeRate) detachedCriteria.getExecutableCriteria(getSession()).list().get(0)).getSellrateMin());

		return (((ExchangeRate) detachedCriteria.getExecutableCriteria(getSession()).list().get(0)));
		// return
		// ((List<ExchangeRate>)findAll(detachedCriteria)).get(0).getSellrateMin();

	}

	@Override
	public OnlineRateAlert isExists(BigDecimal customerId, BigDecimal currencyId, String frequncy) {

		logger.debug("customerId " + customerId);
		logger.debug("currencyId" + currencyId);
		logger.debug("frequncy" + frequncy);

		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(OnlineRateAlert.class, "onlineRateAlert");

		detachedCriteria.setFetchMode("onlineRateAlert.customerId", FetchMode.JOIN);
		detachedCriteria.createAlias("onlineRateAlert.customerId", "customerId", JoinType.INNER_JOIN);
		detachedCriteria.add(Restrictions.eq("customerId.customerId", customerId));

		detachedCriteria.setFetchMode("onlineRateAlert.baseCurrencyId", FetchMode.JOIN);
		detachedCriteria.createAlias("onlineRateAlert.baseCurrencyId", "baseCurrencyId", JoinType.INNER_JOIN);
		detachedCriteria.add(Restrictions.eq("baseCurrencyId.currencyId", currencyId));

		detachedCriteria.setFetchMode("onlineRateAlert.exchangeRateId", FetchMode.SELECT);

		detachedCriteria.add(Restrictions.eq("onlineRateAlert.frequency", frequncy));

		return (((OnlineRateAlert) detachedCriteria.getExecutableCriteria(getSession()).list().get(0)));
	}
}
