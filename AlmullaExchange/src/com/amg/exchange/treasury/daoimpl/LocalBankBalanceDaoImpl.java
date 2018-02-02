package com.amg.exchange.treasury.daoimpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.treasury.bean.LocalBankBalanceDatatable;
import com.amg.exchange.treasury.dao.ILocalBankBalanceDao;
import com.amg.exchange.treasury.model.AccountBalance;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.DailyAccountBalance;
import com.amg.exchange.treasury.service.FundEstimationDetailsBeanService;
import com.amg.exchange.util.StaticValueHolder;

@Repository
public class LocalBankBalanceDaoImpl<T> extends CommonDaoImpl<T> implements ILocalBankBalanceDao {
	
	@Autowired
	FundEstimationDetailsBeanService fundEstimationDetailsBeanService;
	
	/*@SuppressWarnings("unchecked")
	@Override
	public List<LocalBankBalanceDatatable> getLocalBankBalance(BigDecimal applicationCountryId) {
		
		List<DailyAccountBalance> finalAccountBalance = new ArrayList<DailyAccountBalance>();
		List<LocalBankBalanceDatatable> lstShowData = new ArrayList<LocalBankBalanceDatatable>();
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(DailyAccountBalance.class, "dailyAccount");
		
		dCriteria.setFetchMode("dailyAccount.exCurrencyMaster", FetchMode.JOIN);
		dCriteria.createAlias("dailyAccount.exCurrencyMaster", "exCurrencyMaster", JoinType.INNER_JOIN);
		
		dCriteria.setFetchMode("dailyAccount.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("dailyAccount.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", applicationCountryId)); 
		
		List<DailyAccountBalance> lstDailyBankDetails = dCriteria.getExecutableCriteria(getSession()).list();
		
		for (DailyAccountBalance dailyAccountBalance : lstDailyBankDetails) {
			DetachedCriteria dCriteria1 = DetachedCriteria.forClass(BankMaster.class, "bankMaster");
			dCriteria1.add(Restrictions.eq("bankMaster.bankId", dailyAccountBalance.getBankMaster().getBankId()));
			
			dCriteria1.setFetchMode("bankMaster.fsCountryMaster", FetchMode.JOIN);
			dCriteria1.createAlias("bankMaster.fsCountryMaster", "fsCountryMasterBankMaster", JoinType.INNER_JOIN);
			dCriteria1.add(Restrictions.eq("fsCountryMasterBankMaster.countryId", applicationCountryId));
			
			int size =dCriteria1.getExecutableCriteria(getSession()).list().size(); 
			
			if(size>0) {
				finalAccountBalance.add(dailyAccountBalance);
			}
		}
		
		int i=1;
		
		for (DailyAccountBalance dailyAccountBalance : finalAccountBalance) {
			lstShowData.add(new LocalBankBalanceDatatable(i++, 
																									dailyAccountBalance.getBankMaster().getBankFullName(), 
																									round(dailyAccountBalance.getFundLocalCurrencyBalance().doubleValue(), fundEstimationDetailsBeanService.getDecimalValue(dailyAccountBalance.getExCurrencyMaster().getCurrencyId())),
																									round(dailyAccountBalance.getFundLocalCurrencyBalance().doubleValue()*0.061, fundEstimationDetailsBeanService.getDecimalValue(dailyAccountBalance.getExCurrencyMaster().getCurrencyId()))));
		}
		
		return lstShowData;
	}*/
	
	@SuppressWarnings("unchecked")
	@Override
	public List<LocalBankBalanceDatatable> getLocalBankBalance(BigDecimal applicationCountryId, int decimalValue) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(AccountBalance.class, "accountBalance");
		
		dCriteria.setFetchMode("accountBalance.exBankAccountDetails", FetchMode.JOIN);
		dCriteria.createAlias("accountBalance.exBankAccountDetails", "exBankAccountDetails", JoinType.INNER_JOIN);
		
		dCriteria.setFetchMode("accountBalance.bankMaster", FetchMode.JOIN);
		dCriteria.createAlias("accountBalance.bankMaster", "bankMaster", JoinType.INNER_JOIN);
		
		dCriteria.setFetchMode("accountBalance.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("accountBalance.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", applicationCountryId));
		
		dCriteria.setFetchMode("accountBalance.bankApplicability", FetchMode.JOIN);
		dCriteria.createAlias("accountBalance.bankApplicability", "bankApplicability", JoinType.INNER_JOIN);
		
		dCriteria.setFetchMode("bankApplicability.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.fsCountryMaster", "fsApplicationCountryMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsApplicationCountryMaster.countryId", applicationCountryId));
		
		List<AccountBalance> data = (List<AccountBalance>)findAll(dCriteria);
		
		List<LocalBankBalanceDatatable> lstShowData = new ArrayList<LocalBankBalanceDatatable>();
		
		int i = 1;
		for (AccountBalance accountBalance : data) {
			if(accountBalance.getLocalBalance()!=null) {
				lstShowData.add(new LocalBankBalanceDatatable(i++,accountBalance.getBankMaster().getBankCode(), accountBalance.getBankMaster().getBankFullName(), 
						new BigDecimal(round(accountBalance.getLocalBalance().doubleValue(), 3)), new BigDecimal(round(accountBalance.getLocalBalance().doubleValue()*StaticValueHolder.ikonRateForLocal, decimalValue))));
			}
		}
		return lstShowData;
	}
	
	public double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

}
