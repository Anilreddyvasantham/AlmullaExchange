package com.amg.exchange.loyalty.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.loyalty.dao.ILoyaltyAutoEmailConfigurationDao;
import com.amg.exchange.loyalty.model.LoyaltyMailOptions;
import com.amg.exchange.loyalty.service.ILoyaltyAutoEmailConfigurationService;
@Service
public class LoyaltyAutoEmailConfigurationServiceImpl implements
		ILoyaltyAutoEmailConfigurationService {
	@Autowired
	ILoyaltyAutoEmailConfigurationDao iLoyaltyAutoEmailConfigDao;
	
	@Override
	@Transactional
	public void saveRecords(LoyaltyMailOptions loyaltyMailOptions) {
		iLoyaltyAutoEmailConfigDao.saveRecords(loyaltyMailOptions);
		
	}

	@Override
	@Transactional
	public List<LoyaltyMailOptions> getAllRecords() {
	 
		return iLoyaltyAutoEmailConfigDao.getAllRecords();
	}

}
