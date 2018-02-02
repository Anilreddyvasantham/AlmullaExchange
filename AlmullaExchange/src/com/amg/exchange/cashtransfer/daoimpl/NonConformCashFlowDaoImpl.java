package com.amg.exchange.cashtransfer.daoimpl;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.amg.exchange.cashtransfer.dao.INonConformCashFlowDao;
import com.amg.exchange.cashtransfer.model.NonConfirmCashFlow;
import com.amg.exchange.cashtransfer.model.NonConfirmCashFlowDetails;
import com.amg.exchange.common.daoimpl.CommonDaoImpl;

@SuppressWarnings({"rawtypes","unchecked"})
@Repository
public class NonConformCashFlowDaoImpl extends CommonDaoImpl implements INonConformCashFlowDao{

	@Override
	public List<NonConfirmCashFlow> getAllNonConformCashFlowList() {
		DetachedCriteria criteria = DetachedCriteria.forClass(NonConfirmCashFlow.class, "nonConfirmCashFlow");
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<NonConfirmCashFlow>) findAll(criteria);
	}

	@Override
	public List<NonConfirmCashFlowDetails> getdetails(BigDecimal cashHeaderId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(NonConfirmCashFlowDetails.class, "nonConfirmCashFlowDetails");
		criteria.add(Restrictions.eq("nonConfirmCashFlowDetails.cashHeaderId", cashHeaderId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<NonConfirmCashFlowDetails>) findAll(criteria);
	}
}
