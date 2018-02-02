package com.amg.exchange.loyalty.dao;

import java.util.List;

import com.amg.exchange.loyalty.model.LoyaltyLogOptions;

public interface ILoyaltyCalculationLogOptionsDao {
	public void saveRecords(LoyaltyLogOptions loyaltyLogOptionsObj);
	public List<LoyaltyLogOptions> getAllRecords();
}
