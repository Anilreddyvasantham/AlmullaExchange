package com.amg.exchange.remittance.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.remittance.dao.IHighValueCurrencyDao;
import com.amg.exchange.remittance.model.HighValueCurrencySetup;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.util.Constants;
@SuppressWarnings("serial")
@Repository
public class HighValueCurrencyDaoImpl<T> extends CommonDaoImpl<T> implements Serializable,IHighValueCurrencyDao<T> {

	@Override
	public void save(HighValueCurrencySetup highValueCurrencySetup) {

		getSession().saveOrUpdate(highValueCurrencySetup);
	}
	@Override
	public List<HighValueCurrencySetup> getAllCurrencyList(BigDecimal currencyId){
		
		DetachedCriteria criteria = DetachedCriteria.forClass(HighValueCurrencySetup.class, "highValueCurrencySetup");
		
		criteria.setFetchMode("highValueCurrencySetup.currencyId", FetchMode.JOIN);
		criteria.createAlias("highValueCurrencySetup.currencyId", "currencyId", JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("currencyId.currencyId", currencyId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<HighValueCurrencySetup> objList = (List<HighValueCurrencySetup>) findAll(criteria);

		if (objList.isEmpty())
			return null;

		return objList;
		
	}
	
	@Override
	public List<HighValueCurrencySetup> getEnquiryList(){
		
		DetachedCriteria criteria = DetachedCriteria.forClass(HighValueCurrencySetup.class, "highValueCurrencySetup");
		
		criteria.setFetchMode("highValueCurrencySetup.currencyId", FetchMode.JOIN);
		criteria.createAlias("highValueCurrencySetup.currencyId", "currencyId", JoinType.INNER_JOIN);
		List<HighValueCurrencySetup> objList = (List<HighValueCurrencySetup>) findAll(criteria);

		if (objList.isEmpty())
			return null;

		return objList;
		
	}
	
	@Override
	public String approveReord(BigDecimal highValueId, String userName) {
		
		String approveMsg;
		HighValueCurrencySetup highValueCurrencySetup =(HighValueCurrencySetup) getSession().get(HighValueCurrencySetup.class, highValueId);
		String approvedUser=highValueCurrencySetup.getApprovedBy();
		if(approvedUser==null)
		{
			highValueCurrencySetup.setIsActive(Constants.Yes);
			highValueCurrencySetup.setApprovedBy(userName);
			highValueCurrencySetup.setApprovedDate(new Date());
			highValueCurrencySetup.setRemarks("");
			getSession().update(highValueCurrencySetup);
			approveMsg="Success";
		}
		else{
			approveMsg="Fail";
		}
		return  approveMsg;
	}
	
	@Override
	public void delete(BigDecimal highValueId) {

		HighValueCurrencySetup highValueCurrencySetup = (HighValueCurrencySetup) getSession().get(HighValueCurrencySetup.class, highValueId);
		getSession().delete(highValueCurrencySetup);

	}
	
	@Override
	public List<CurrencyMaster> getCurrencyList(BigDecimal currencyId){
		
		DetachedCriteria criteria = DetachedCriteria.forClass(CurrencyMaster.class, "currencyMaster");
		
		/*criteria.setFetchMode("currencyMaster.currencyId", FetchMode.JOIN);
		criteria.createAlias("currencyMaster.currencyId", "currencyId", JoinType.INNER_JOIN);
		*/
		criteria.add(Restrictions.eq("currencyMaster.currencyId", currencyId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CurrencyMaster> objList = (List<CurrencyMaster>) findAll(criteria);

		if (objList.isEmpty())
			return null;

		return objList;
		
	}
}
