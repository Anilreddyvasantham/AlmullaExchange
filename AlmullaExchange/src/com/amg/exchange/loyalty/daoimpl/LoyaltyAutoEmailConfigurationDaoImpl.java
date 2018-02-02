package com.amg.exchange.loyalty.daoimpl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.loyalty.dao.ILoyaltyAutoEmailConfigurationDao;
import com.amg.exchange.loyalty.model.LoyaltyMailOptions;

@SuppressWarnings({"rawtypes","unchecked"})
@Repository
public class LoyaltyAutoEmailConfigurationDaoImpl extends CommonDaoImpl  implements
		ILoyaltyAutoEmailConfigurationDao {

	@Override
	public void saveRecords(LoyaltyMailOptions loyaltyMailOptions) {
	 getSession().saveOrUpdate(loyaltyMailOptions );
		
	}

	@Override
	public List<LoyaltyMailOptions> getAllRecords() {
		DetachedCriteria criteria=DetachedCriteria.forClass(LoyaltyMailOptions.class,"loyaltyMailOptions");
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<LoyaltyMailOptions>)findAll(criteria);
		 
	}
	

}
