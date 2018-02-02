package com.amg.exchange.common.daoimpl;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.dao.IPurposeOfTransctionDao;
import com.amg.exchange.complaint.model.ComplaintAssigned;
import com.amg.exchange.foreigncurrency.model.PurposeOfTransaction;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Repository
public class PurposeOfTransctionDaoImpl extends CommonDaoImpl implements IPurposeOfTransctionDao {

	@Override
	public List<String> toFetchPurposeOfCodeList(String query) {
		DetachedCriteria criteria = DetachedCriteria.forClass(PurposeOfTransaction.class, "purposeOfTransaction");
		criteria.add(Restrictions.like("purposeOfTransaction.purposeOfCode", query, MatchMode.ANYWHERE).ignoreCase());
		criteria.setProjection(Projections.property("purposeOfTransaction.purposeOfCode"));
		criteria.addOrder(Order.asc("purposeOfTransaction.purposeOfCode"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<String> lstPurposeOfTransaction = (List<String>) findAll(criteria);
		return lstPurposeOfTransaction;
	}

	@Override
	public List<PurposeOfTransaction> toPurposeOfCodeAllValues(String purposeOfCode) {
		DetachedCriteria criteria = DetachedCriteria.forClass(PurposeOfTransaction.class, "purposeOfTransaction");
		criteria.setFetchMode("purposeOfTransaction.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("purposeOfTransaction.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("purposeOfTransaction.purposeOfCode", purposeOfCode));
		List<PurposeOfTransaction> lstPurposeOfTransaction = (List<PurposeOfTransaction>) findAll(criteria);
		return lstPurposeOfTransaction;
	}

	@Override
	public List<PurposeOfTransaction> toPurposeNamebyId(BigDecimal purposeId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(PurposeOfTransaction.class, "purposeOfTransaction");
		criteria.setFetchMode("purposeOfTransaction.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("purposeOfTransaction.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("purposeOfTransaction.purposeId", purposeId));
		List<PurposeOfTransaction> lstPurposeOfTransaction = (List<PurposeOfTransaction>) findAll(criteria);
		return lstPurposeOfTransaction;
	}

}
