package com.amg.exchange.loyalty.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.loyalty.dao.ILoyaltySchedulerDao;
import com.amg.exchange.loyalty.model.LoyaltyScheduler;
import com.amg.exchange.loyalty.service.ILoyaltySchedulerService;
@Service
public class LoyaltySchedulerServiceImpl implements ILoyaltySchedulerService {
@Autowired
ILoyaltySchedulerDao iLoyaltyDao;
	
	@Override
	@Transactional
	public void saveOrUpdate(LoyaltyScheduler loyaltySchedularObj) {
		iLoyaltyDao.saveOrUpdate(loyaltySchedularObj);
		
	}

	@Override
	@Transactional
	public List<LoyaltyScheduler> dulaicateCheck() {
		 
		return iLoyaltyDao.dulaicateCheck();
	}

}
