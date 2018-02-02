/**
 * 
 */
package com.amg.exchange.common.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.util.AMGException;

/**
 * @author Subramaniam
 *
 */
public interface IUserFinancialYearService {
	
	public Hashtable<String,Date> getFinancialDatevalues(BigDecimal financialYear) throws AMGException;

	public List<BigDecimal> getUserFinancialYear(BigDecimal financeYear);
	
	public List<UserFinancialYear> getUserFinancialYearList();
	
	public Boolean isExistUserFinancialYear(BigDecimal financeYear);
	
	public void save(UserFinancialYear userFinancialYear);
}
