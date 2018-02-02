package com.amg.exchange.loyalty.service;

import java.util.List;

import com.amg.exchange.loyalty.model.LoyaltyLogOptions;

public interface ILoyaltyCalculationLogOptionsService {

	public void saveRecords(LoyaltyLogOptions loyaltyLogOptionsObj);
	public List<LoyaltyLogOptions> getAllRecords();

	
	
}
