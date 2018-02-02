package com.amg.exchange.loyalty.service;

import java.util.List;

import com.amg.exchange.loyalty.model.LoyaltyScheduler;

public interface ILoyaltySchedulerService {
public void saveOrUpdate(LoyaltyScheduler loyaltySchedularObj);
public List<LoyaltyScheduler> dulaicateCheck();
}
