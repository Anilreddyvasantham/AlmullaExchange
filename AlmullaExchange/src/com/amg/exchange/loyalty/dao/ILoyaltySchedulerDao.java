package com.amg.exchange.loyalty.dao;

import java.util.List;

import com.amg.exchange.loyalty.model.LoyaltyScheduler;

public interface ILoyaltySchedulerDao {
	public void saveOrUpdate(LoyaltyScheduler loyaltySchedularObj) ;
	public List<LoyaltyScheduler> dulaicateCheck() ;
}
