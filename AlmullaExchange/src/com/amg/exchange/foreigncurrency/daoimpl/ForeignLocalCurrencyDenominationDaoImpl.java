package com.amg.exchange.foreigncurrency.daoimpl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CustomHibernateDaoSupport;
import com.amg.exchange.foreigncurrency.bean.ForeignCurrencyPurchageReport;
import com.amg.exchange.foreigncurrency.dao.ForeignLocalCurrencyDenominationDao;
import com.amg.exchange.foreigncurrency.model.Collect;
import com.amg.exchange.foreigncurrency.model.CurrencyWiseDenomination;
import com.amg.exchange.foreigncurrency.model.Stock;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.FundEstimationDetails;
import com.amg.exchange.util.Constants;

@Repository
public class ForeignLocalCurrencyDenominationDaoImpl<T> extends CustomHibernateDaoSupport implements ForeignLocalCurrencyDenominationDao<T>{

	@SuppressWarnings("unchecked")
	@Override
	public List<CurrencyWiseDenomination> getLocalCurrencyDenomination(BigDecimal countryId, String userName, String countryBranchId, BigDecimal companyId, String currecnId) {
		
		System.out.println(countryId+"----"+userName+"---"+countryBranchId+"---"+companyId+"---"+currecnId);
		
		DetachedCriteria criteria = DetachedCriteria.forClass(CurrencyWiseDenomination.class, "currencyDenomination");
		
		criteria.setFetchMode("currencyDenomination.exStock", FetchMode.JOIN);
		criteria.createAlias("currencyDenomination.exStock", "exStock", JoinType.INNER_JOIN);
		
		/*criteria.setFetchMode("exStock.countryBranch", FetchMode.JOIN);
		criteria.createAlias("exStock.countryBranch", "countryBranch", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("countryBranch.countryBranchId",  new BigDecimal(countryBranchId)));*/
		
		/*criteria.setFetchMode("currencyDenomination.fsCompanyMaster", FetchMode.JOIN);
		criteria.createAlias("currencyDenomination.fsCompanyMaster", "fsCompanyMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCompanyMaster.companyId",  companyId));*/
		
	/*	criteria.setFetchMode("currencyDenomination.exCurrencyMaster", FetchMode.JOIN);
		criteria.createAlias("currencyDenomination.exCurrencyMaster", "exCurrencyMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("exCurrencyMaster.currencyId",  new BigDecimal(currecnId)));*/
		
		criteria.add(Restrictions.eq("exStock.oracleUser", userName));
		
		criteria.setFetchMode("currencyDenomination.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("currencyDenomination.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		criteria.addOrder(Order.desc("denominationAmount"));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		return (List<CurrencyWiseDenomination>)criteria.getExecutableCriteria(getSession()).list();
	}

	@Override
	public void updateStock(BigDecimal pk, int closingQuantity, String userName) {
		
		 System.out.println("Here ::::::::::::::::"+pk +"::::::::::::::::::::::::::::::"+closingQuantity+":::::::::::::::::::::"+userName);
		 
		 //here we have user name but we don't need, because we have primary key
		 Stock stock = (Stock)getSession().get(Stock.class, pk); 
		 stock.setSaleQuantity(closingQuantity);
		 stock.setModifiedBy(userName);
		 stock.setModifiedDate(new Date());
		 stock.setLogDate(new Date());
		 getSession().update(stock); 
	}

	@Override
	public void updateRefund(BigDecimal pk, String refundedCash, String userName) {
		
		 System.out.println("Here ::::::::::::::::::"+pk+"::::::::::::::::::::::"+refundedCash);
		 Collect collect = (Collect)getSession().get(Collect.class, pk); 
		 collect.setRefoundAmount(new BigDecimal(refundedCash));
		 collect.setModifiedBy(userName);
		 collect.setModifiedDate(new Date());
		 getSession().update(collect);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getDecimalPerCountry(BigDecimal countryId) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(CurrencyMaster.class, "currencymaster");
		
		criteria.setFetchMode("currencymaster.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("currencymaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		
		criteria.addOrder(Order.asc("currencymaster.currencyCode"));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<CurrencyMaster> data = criteria.getExecutableCriteria(getSession()).list();
		
		return Integer.parseInt(data.get(0).getDecinalNumber().toPlainString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getDecimalPerCurrency(BigDecimal currencyId) {
		int decimal = 0;
		DetachedCriteria criteria = DetachedCriteria.forClass(CurrencyMaster.class, "currencymaster");
		criteria.add(Restrictions.eq("currencymaster.currencyId", currencyId));
		criteria.addOrder(Order.asc("currencymaster.currencyCode"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CurrencyMaster> data = criteria.getExecutableCriteria(getSession()).list();
		if(data != null && data.size() != 0){
			decimal = data.get(0).getDecinalNumber().intValue();
		}
		
		return decimal;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Stock> getLocalCurrencyDenominationFromStock(BigDecimal countryId, String userName, String countryBranchId, BigDecimal companyId, String currecnId) {
		
		System.out.println(countryId+"----"+userName+"---"+countryBranchId+"---"+companyId+"---"+currecnId);
		
		String sql =" LOG_DATE =trunc(sysdate)";
		
		DetachedCriteria criteria = DetachedCriteria.forClass(Stock.class, "stock");
		
		criteria.setFetchMode("stock.currencyId", FetchMode.JOIN);
		criteria.createAlias("stock.currencyId", "currencyId",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("currencyId.currencyId",  new BigDecimal(currecnId)));
		
		criteria.setFetchMode("stock.denominationId", FetchMode.JOIN);
		criteria.createAlias("stock.denominationId", "denominationId",JoinType.INNER_JOIN);
		
		criteria.setFetchMode("stock.countryBranch", FetchMode.JOIN);
		criteria.createAlias("stock.countryBranch", "countryBranch", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("countryBranch.countryBranchId",  new BigDecimal(countryBranchId)));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		/*criteria.setFetchMode("exStock.countryBranch", FetchMode.JOIN);
		criteria.createAlias("exStock.countryBranch", "countryBranch", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("countryBranch.countryBranchId",  new BigDecimal(countryBranchId)));*/
		
		/*criteria.setFetchMode("currencyDenomination.fsCompanyMaster", FetchMode.JOIN);
		criteria.createAlias("currencyDenomination.fsCompanyMaster", "fsCompanyMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCompanyMaster.companyId",  companyId));*/
		
	/*	criteria.setFetchMode("currencyDenomination.exCurrencyMaster", FetchMode.JOIN);
		criteria.createAlias("currencyDenomination.exCurrencyMaster", "exCurrencyMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("exCurrencyMaster.currencyId",  new BigDecimal(currecnId)));*/
		
		criteria.add(Restrictions.eq("stock.oracleUser", userName));
		
		
		criteria.setFetchMode("denominationId.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("denominationId.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		criteria.add(Restrictions.sqlRestriction(sql)); 
		
		criteria.addOrder(Order.desc("denominationId.denominationAmount"));
	/*	criteria.add(Restrictions.eq("stock.logDate",new SimpleDateFormat("dd/MM/yyyy"))
		
		  SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        
        setBnkValueDate(format.format(event.getObject()));
        
		.parse(new Date())));*/
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<Stock> data = (List<Stock>)criteria.getExecutableCriteria(getSession()).list();
		List<Stock> finalData = new ArrayList<Stock>();
		for (Stock Stock : data) {
			if(new SimpleDateFormat("dd/MMM/yy").format(Stock.getLogDate()).equalsIgnoreCase(new SimpleDateFormat("dd/MMM/yy").format(new Date()))) {
				finalData.add(Stock);
			}
		}
		
		
		
		return finalData;
	}

	@Override
	public List<Stock> getCurrencyDenominationFromStock(BigDecimal countryId,
			String userName, String countryBranchId, BigDecimal companyId,
			String currecnId) {
		
		System.out.println(countryId+"----"+userName+"---"+countryBranchId+"---"+companyId+"---"+currecnId);
		
		String sql =" LOG_DATE =trunc(sysdate)";
		
		DetachedCriteria criteria = DetachedCriteria.forClass(Stock.class, "stock");
		
		/*criteria.setFetchMode("stock.currencyId", FetchMode.JOIN);
		criteria.createAlias("stock.currencyId", "currencyId",JoinType.INNER_JOIN);*/
		
		criteria.setFetchMode("stock.denominationId", FetchMode.JOIN);
		criteria.createAlias("stock.denominationId", "denominationId",JoinType.INNER_JOIN);
		
		criteria.setFetchMode("stock.countryBranch", FetchMode.JOIN);
		criteria.createAlias("stock.countryBranch", "countryBranch", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("countryBranch.countryBranchId",  new BigDecimal(countryBranchId)));
		
		/*criteria.setFetchMode("exStock.countryBranch", FetchMode.JOIN);
		criteria.createAlias("exStock.countryBranch", "countryBranch", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("countryBranch.countryBranchId",  new BigDecimal(countryBranchId)));*/
		
		/*criteria.setFetchMode("currencyDenomination.fsCompanyMaster", FetchMode.JOIN);
		criteria.createAlias("currencyDenomination.fsCompanyMaster", "fsCompanyMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCompanyMaster.companyId",  companyId));*/
		
		criteria.setFetchMode("denominationId.exCurrencyMaster", FetchMode.JOIN);
		criteria.createAlias("denominationId.exCurrencyMaster", "exCurrencyMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("exCurrencyMaster.currencyId",  new BigDecimal(currecnId)));	
		criteria.add(Restrictions.eq("stock.oracleUser", userName));
		
		criteria.add(Restrictions.sqlRestriction(sql)); 
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		/*criteria.setFetchMode("denominationId.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("denominationId.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));*/
		
		/*criteria.setFetchMode("denominationId.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("denominationId.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));*/
		
		criteria.addOrder(Order.desc("denominationId.denominationAmount"));
	/*	criteria.add(Restrictions.eq("stock.logDate",new SimpleDateFormat("dd/MM/yyyy"))
		
		  SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        
        setBnkValueDate(format.format(event.getObject()));
        
		.parse(new Date())));*/
		List<Stock> data = (List<Stock>)criteria.getExecutableCriteria(getSession()).list();
		List<Stock> finalData = new ArrayList<Stock>();
		for (Stock Stock : data) {
			if(new SimpleDateFormat("dd/MMM/yy").format(Stock.getLogDate()).equalsIgnoreCase(new SimpleDateFormat("dd/MMM/yy").format(new Date()))) {
				finalData.add(Stock);
			}
		}
		
		
		
		return finalData;
	}

	@Override
	public int getDecimalPerCurrencyBasedOnQuoteName(String quoteName) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CurrencyMaster.class, "currencymaster");
		criteria.add(Restrictions.eq("currencymaster.quoteName", quoteName));
	
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<CurrencyMaster> data = criteria.getExecutableCriteria(getSession()).list();
		return Integer.parseInt(data.get(0).getDecinalNumber().toPlainString()) ;
	 
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CurrencyWiseDenomination> getLocalCurrencyDenominationStockWithoutUser(BigDecimal countryId, String currecnId) {
		
		//System.out.println(countryId+"-------"+countryBranchId+"---"+companyId+"---"+currecnId);
		
		DetachedCriteria criteria = DetachedCriteria.forClass(CurrencyWiseDenomination.class, "currencyWiseDenomination");
		
		criteria.setFetchMode("currencyWiseDenomination.exCurrencyMaster", FetchMode.JOIN);
		criteria.createAlias("currencyWiseDenomination.exCurrencyMaster", "exCurrencyMaster",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("exCurrencyMaster.currencyId",  new BigDecimal(currecnId)));
		
		
		
		criteria.setFetchMode("currencyWiseDenomination.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("currencyWiseDenomination.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		
		criteria.add(Restrictions.eq("currencyWiseDenomination.isActive", Constants.Yes));
		criteria.addOrder(Order.desc("currencyWiseDenomination.denominationAmount"));
	
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<CurrencyWiseDenomination> data = (List<CurrencyWiseDenomination>)criteria.getExecutableCriteria(getSession()).list();
		
		if(data.isEmpty())
			return null;
		
		
		return data;
	}
	
}
