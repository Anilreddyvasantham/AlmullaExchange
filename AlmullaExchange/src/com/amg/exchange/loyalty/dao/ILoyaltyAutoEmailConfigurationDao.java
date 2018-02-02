package com.amg.exchange.loyalty.dao;

import java.util.List;

import com.amg.exchange.loyalty.model.LoyaltyMailOptions;

public interface ILoyaltyAutoEmailConfigurationDao {
	public void saveRecords(LoyaltyMailOptions loyaltyMailOptions);
	public List<LoyaltyMailOptions> getAllRecords();
}
