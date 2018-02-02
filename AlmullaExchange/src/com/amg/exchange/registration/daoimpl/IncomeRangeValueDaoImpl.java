package com.amg.exchange.registration.daoimpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.registration.dao.IIncomeRangeValueDao;
import com.amg.exchange.registration.model.IncomeRangeMaster;
import com.amg.exchange.registration.model.IncomeRangeValue;
import com.amg.exchange.treasury.model.DocumentSeriality;
import com.amg.exchange.util.Constants;

@SuppressWarnings("serial")
@Repository
public class IncomeRangeValueDaoImpl<T> extends CommonDaoImpl<T> implements IIncomeRangeValueDao {

	public IncomeRangeValueDaoImpl() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void save(IncomeRangeValue incomeRangeValue) {

		getSession().saveOrUpdate(incomeRangeValue);
	}

	@Override
	public List<IncomeRangeValue> getIncomeRangeValueList() {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(IncomeRangeValue.class, "incomeRangeValue");

		

		criteria.addOrder(Order.asc("incomeRangeValue.incomeValueTransactionId"));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<IncomeRangeValue> objList = (List<IncomeRangeValue>) findAll(criteria);

		if (objList.isEmpty())
			return null;

		return objList;		
	}
	
	@Override
	public void delete(BigDecimal incomeRangeValueId) {

		IncomeRangeValue incomeRangeValue = (IncomeRangeValue) getSession().get(IncomeRangeValue.class, incomeRangeValueId);
		getSession().delete(incomeRangeValue);

	}

	@Override
	public void activateRecord(BigDecimal incomeRangeValueId, String userName) {
		IncomeRangeValue incomeRangeValue = (IncomeRangeValue) getSession().get(IncomeRangeValue.class, incomeRangeValueId);
		incomeRangeValue.setIsActive(Constants.U);
		incomeRangeValue.setModifiedBy(userName);
		incomeRangeValue.setModifiedDate(new Date());
		incomeRangeValue.setApprovedBy(null);
		incomeRangeValue.setApprovedDate(null);
		incomeRangeValue.setRemarks(null);
		getSession().update(incomeRangeValue);

	}
	
	
	@Override
	public boolean isExistIncomeRange(BigDecimal appCountryId,BigDecimal fromAmount, BigDecimal toAmount) {

		DetachedCriteria criteria = DetachedCriteria.forClass(IncomeRangeValue.class, "incomeRangeValue");
		
		criteria.setFetchMode("incomeRangeValue.appCountryId", FetchMode.JOIN);
		criteria.createAlias("incomeRangeValue.appCountryId", "appCountryId", JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("appCountryId.countryId", appCountryId));
		
		criteria.add(Restrictions.and(Restrictions.ge("incomeRangeValue.incomeRangeFrom", fromAmount),Restrictions.le("incomeRangeValue.incomeRangeTo", toAmount)));
	

		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<IncomeRangeMaster> data = (List<IncomeRangeMaster>) findAll(criteria);
		
		System.out.println(""+data.size());

		if (data.isEmpty())
			return true;

		return false;
	}
	
	
	@Override
	public List<IncomeRangeValue> getIncomeFRangeValueListforDetail(BigDecimal countryId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(IncomeRangeValue.class, "incomeRangeValue");
		criteria.setFetchMode("incomeRangeValue.appCountryId", FetchMode.JOIN);
		criteria.createAlias("incomeRangeValue.appCountryId", "appCountryId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("appCountryId.countryId", countryId));
		
		return (List<IncomeRangeValue>) findAll(criteria);
	}
}
