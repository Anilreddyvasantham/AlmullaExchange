package com.amg.exchange.remittance.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.registration.model.RoleMaster;
import com.amg.exchange.remittance.dao.ICashLimitDao;
import com.amg.exchange.remittance.model.CashLimit;
import com.amg.exchange.remittance.model.CashLimitType;
import com.amg.exchange.remittance.service.ICashLimitService;

@Service("cashLimitservice")
public class CashLimitServiceImpl implements ICashLimitService  {
	

	
	@Autowired
	ICashLimitDao cashLimitdao;

	@Override
	@Transactional
	public void save(CashLimit cashLimit) {
		cashLimitdao.save(cashLimit);
	}

	@Override
	@Transactional
	public List<CashLimitType> getCashLimitType() {
		return cashLimitdao.getCashLimitType();
	}

	@Override
	@Transactional
	public List<CashLimit> populateDetails(BigDecimal countryId, BigDecimal limitId) {
		return cashLimitdao.populateDetails(countryId,limitId);
	}

	@Override
	@Transactional
	public List<CashLimit> viewbasedOnCountry(BigDecimal countryId) {
		return cashLimitdao.viewbasedOnCountry(countryId);
	}

	@Override
	@Transactional
	public List<RoleMaster> getRoleMasterList() {
		return cashLimitdao.getRoleMasterList();
		
	}

	
}