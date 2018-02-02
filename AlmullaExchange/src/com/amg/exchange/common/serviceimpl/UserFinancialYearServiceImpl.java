/**
 * 
 */
package com.amg.exchange.common.serviceimpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.common.dao.IUserFinancialYearDao;
import com.amg.exchange.common.service.IUserFinancialYearService;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.util.AMGException;

/**
 * @author Subramaniam
 * 
 */
@Service("userFinancialYearService")
public class UserFinancialYearServiceImpl implements IUserFinancialYearService {
	@Autowired
	IUserFinancialYearDao userFinancialYearDao;

	public IUserFinancialYearDao getUserFinancialYearDao() {
		return userFinancialYearDao;
	}

	public void setUserFinancialYearDao(IUserFinancialYearDao userFinancialYearDao) {
		this.userFinancialYearDao = userFinancialYearDao;
	}

	@Override
	@Transactional
	public Hashtable<String,Date> getFinancialDatevalues(BigDecimal financialYear) throws AMGException {
		// TODO Auto-generated method stub
		return getUserFinancialYearDao().getFinancialDatevalues(financialYear);
		
	}
	
	@Override
	@Transactional
	public List<BigDecimal> getUserFinancialYear(BigDecimal financeYear){
		return getUserFinancialYearDao().getUserFinancialYear(financeYear);
	}
	@Override
	@Transactional
	public List<UserFinancialYear> getUserFinancialYearList(){
		return getUserFinancialYearDao().getUserFinancialYearList();
	}
	@Override
	@Transactional
	public Boolean isExistUserFinancialYear(BigDecimal financeYear){
		return getUserFinancialYearDao().isExistUserFinancialYear(financeYear);
	}
	@Override
	@Transactional
	public void save(UserFinancialYear userFinancialYear){
		getUserFinancialYearDao().save(userFinancialYear);
		
	}
}
