package com.amg.exchange.loyalty.daoimpl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.loyalty.dao.ILoyaltyCalculationLogOptionsDao;
import com.amg.exchange.loyalty.model.LoyaltyLogOptions;

@SuppressWarnings("rawtypes")
@Repository
public class LoyaltyCalculationLogOptionsDaoImpl extends CommonDaoImpl  implements
		ILoyaltyCalculationLogOptionsDao {

	@Override
	public void saveRecords(LoyaltyLogOptions loyaltyLogOptionsObj) {
	 getSession().saveOrUpdate(loyaltyLogOptionsObj );		
	}

	@Override
	public List<LoyaltyLogOptions> getAllRecords() {
		DetachedCriteria criteria=DetachedCriteria.forClass(LoyaltyLogOptions.class,"loyaltyLogOptions");
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<LoyaltyLogOptions>)findAll(criteria);
	 
	}
	

}
