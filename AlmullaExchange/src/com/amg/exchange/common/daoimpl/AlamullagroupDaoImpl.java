package com.amg.exchange.common.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.amg.exchange.common.dao.IAlamullagroupDao;
import com.amg.exchange.common.model.MarketingData;

@SuppressWarnings("serial")
@Repository
public class AlamullagroupDaoImpl<T> extends CommonDaoImpl<T> implements IAlamullagroupDao<T>, Serializable {

	
	private static final Logger LOG = Logger.getLogger(AlamullagroupDaoImpl.class);
	@SuppressWarnings("unchecked")
	@Override
	public List<MarketingData> getCountryDetails(BigDecimal langId, BigDecimal countryId) {
		LOG.info("Entering into getCountryDetails ");
		LOG.info("langId== " + langId);
		LOG.info("countryId== " + countryId);
		DetachedCriteria criteria = DetachedCriteria.forClass(MarketingData.class, "marketingData");
		criteria.setFetchMode("marketingData.applicationCountry", FetchMode.JOIN);
		criteria.createAlias("marketingData.applicationCountry", "applicationCountry");
		criteria.add(Restrictions.eq("applicationCountry.countryId", countryId));
		criteria.setFetchMode("marketingData.marklangtype", FetchMode.JOIN);
		criteria.createAlias("marketingData.marklangtype", "marklangtype");
		criteria.add(Restrictions.eq("marklangtype.languageId", langId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOG.info("Exit into getCountryDetails ");
		return (List<MarketingData>) ((List<MarketingData>) findAll(criteria));
	}
}
