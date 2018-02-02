package com.amg.exchange.loyalty.service;

import java.util.List;

import com.amg.exchange.loyalty.model.LoyaltyMailOptions;

public interface ILoyaltyAutoEmailConfigurationService {

	public void saveRecords(LoyaltyMailOptions  loyaltyMailOptions);
	public List<LoyaltyMailOptions> getAllRecords();
	

}
