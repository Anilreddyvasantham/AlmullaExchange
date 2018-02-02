package com.amg.exchange.registration.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.registration.dao.IIncomeRangeValueDao;
import com.amg.exchange.registration.model.IncomeRangeValue;
import com.amg.exchange.registration.service.IIncomeRangeValueService;
@SuppressWarnings("serial")
@Service("incomeRangeValueServiceImpl")
public class IncomeRangeValueServiceImpl implements IIncomeRangeValueService {
	
	
	@Autowired
	IIncomeRangeValueDao incomeRangeValueDao;

	public IncomeRangeValueServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	
	
	@Transactional
	@Override
	public void save(IncomeRangeValue incomeRangeValue) {
		incomeRangeValueDao.save(incomeRangeValue);
				
	}
	@Transactional
	@Override
	public List<IncomeRangeValue> getIncomeRangeValueList() {
		
		return incomeRangeValueDao.getIncomeRangeValueList();
	}
	@Transactional
	@Override
	public void activateRecord(BigDecimal incomeRangeValueId, String userName) {
		incomeRangeValueDao.activateRecord(incomeRangeValueId, userName);				
	}
	@Transactional
	@Override
	public void delete(BigDecimal incomeRangeValueId) {
		incomeRangeValueDao.delete(incomeRangeValueId);		
		
	}
	@Transactional
	@Override
	public boolean isExistIncomeRange(BigDecimal appCountryId,BigDecimal fromAmount, BigDecimal toAmount){
		return incomeRangeValueDao.isExistIncomeRange(appCountryId, fromAmount, toAmount);
	}
	@Transactional
	@Override
	public List<IncomeRangeValue> getIncomeFRangeValueListforDetail(BigDecimal countryId){
		return incomeRangeValueDao.getIncomeFRangeValueListforDetail(countryId);
	}
}
