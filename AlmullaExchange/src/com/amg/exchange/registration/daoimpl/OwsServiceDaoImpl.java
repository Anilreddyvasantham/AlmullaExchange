package com.amg.exchange.registration.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.model.OwsSchedule;
import com.amg.exchange.registration.dao.IOwsDao;

@Repository
@SuppressWarnings("unchecked")
public class OwsServiceDaoImpl<T> extends CommonDaoImpl<T> implements IOwsDao, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void save(OwsSchedule owsSchedule) {
		getSession().saveOrUpdate(owsSchedule);
	}

	@Override
	public OwsSchedule populateData(BigDecimal countryId, BigDecimal bankId, BigDecimal appcountryId) {
		System.out.println("Entering into populateData method");
		DetachedCriteria criteria = DetachedCriteria.forClass(OwsSchedule.class, "owsSchedule");
		criteria.setFetchMode("owsSchedule.bankCountry", FetchMode.JOIN);
		criteria.createAlias("owsSchedule.bankCountry", "bankCountry", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("bankCountry.countryId", countryId));
		criteria.setFetchMode("owsSchedule.bank", FetchMode.JOIN);
		criteria.createAlias("owsSchedule.bank", "bank", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("bank.bankId", bankId));
		criteria.setFetchMode("owsSchedule.applicationCountry", FetchMode.JOIN);
		criteria.createAlias("owsSchedule.applicationCountry", "applicationCountry", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("applicationCountry.countryId", appcountryId));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		System.out.println("Entering into populateData method");
		List<OwsSchedule> owsSchedule = (List<OwsSchedule>) findAll(criteria);
		if (null != owsSchedule) {
			System.out.println("^^^^^^" + owsSchedule.size());
			if (!owsSchedule.isEmpty()) {
				return owsSchedule.get(0);
			} else {
				return null;
			}
		}
		return null;
	}
}
