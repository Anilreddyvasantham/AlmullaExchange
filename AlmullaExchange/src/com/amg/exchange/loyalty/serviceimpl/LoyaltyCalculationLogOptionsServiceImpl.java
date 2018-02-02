package com.amg.exchange.loyalty.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.loyalty.dao.ILoyaltyCalculationLogOptionsDao;
import com.amg.exchange.loyalty.model.LoyaltyLogOptions;
import com.amg.exchange.loyalty.service.ILoyaltyCalculationLogOptionsService;
@Service
public class LoyaltyCalculationLogOptionsServiceImpl implements
		ILoyaltyCalculationLogOptionsService {
	
	@Autowired
	ILoyaltyCalculationLogOptionsDao iLoyaltyCalculationOptionsDao;

	@Override
	@Transactional
	public void saveRecords(LoyaltyLogOptions loyaltyLogOptionsObj) {
			iLoyaltyCalculationOptionsDao.saveRecords(loyaltyLogOptionsObj);
	}

	@Override
	@Transactional
	public List<LoyaltyLogOptions> getAllRecords() {
		 		return iLoyaltyCalculationOptionsDao.getAllRecords();
	}
	

}
