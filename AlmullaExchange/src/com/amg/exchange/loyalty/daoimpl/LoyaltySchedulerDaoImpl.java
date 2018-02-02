package com.amg.exchange.loyalty.daoimpl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.complaint.model.ComplaintActionDesc;
import com.amg.exchange.loyalty.dao.ILoyaltySchedulerDao;
import com.amg.exchange.loyalty.model.LoyaltyScheduler;
import com.amg.exchange.treasury.model.Document;

@SuppressWarnings("rawtypes")
@Repository
public class LoyaltySchedulerDaoImpl extends CommonDaoImpl implements ILoyaltySchedulerDao {

	@Override
	public void saveOrUpdate(LoyaltyScheduler loyaltySchedularObj) {
	 getSession().saveOrUpdate(loyaltySchedularObj );
		
	}

	@Override
	public List<LoyaltyScheduler> dulaicateCheck() {
		DetachedCriteria criteria=DetachedCriteria.forClass(LoyaltyScheduler.class,"loyaltyScheduler");
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<LoyaltyScheduler>)findAll(criteria);
	}

}
