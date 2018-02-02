package com.amg.exchange.stock.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.dto.CurrencyMasterDTO;
import com.amg.exchange.foreigncurrency.model.CurrencyWiseDenomination;
import com.amg.exchange.foreigncurrency.model.ForeignCurrencyAdjust;
import com.amg.exchange.foreigncurrency.model.Stock;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.stock.bean.StockAdjustmentBean;
import com.amg.exchange.stock.dao.IStockAdjustmentDao;

@Repository
public class StockAdjustmentDaoImpl<T>  extends CommonDaoImpl<T> implements IStockAdjustmentDao<T>,Serializable{

	private static final Logger log=Logger.getLogger(StockAdjustmentBean.class);
	private static final long serialVersionUID = 1L;

	@Override
	public List<Employee> getAllStaffList(BigDecimal branchId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Employee.class, "employee");
		
		criteria.setFetchMode("employee.fsCountryBranch", FetchMode.JOIN);
		criteria.createAlias("employee.fsCountryBranch", "fsCountryBranch",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCountryBranch.countryBranchId", branchId));
			
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<Employee>)findAll(criteria);
	}

	@Override
	public List<CurrencyMasterDTO> getCurrencyFromStock(
			BigDecimal countryBranchId, String userName) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(Stock.class, "stock");
		
		criteria.setFetchMode("stock.currencyId", FetchMode.JOIN);
		criteria.createAlias("stock.currencyId", "currencyId",JoinType.INNER_JOIN);
		
		criteria.setFetchMode("stock.countryBranch", FetchMode.JOIN);
		criteria.createAlias("stock.countryBranch", "countryBranch",JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("countryBranch.countryBranchId", countryBranchId));
		
		criteria.add(Restrictions.eq("stock.oracleUser", userName));
		
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yy");
		try {
			String sdate=df.format(new Date());
			Date fromDate = df.parse(sdate);
			criteria.add(Restrictions.eq(("stock.logDate"), fromDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		ProjectionList columns = Projections.projectionList();
		
		columns.add(Projections.distinct(Projections.property("currencyId.currencyId")), "currencyId");
		columns.add(Projections.property("currencyId.currencyCode"),"currencyCode");
		columns.add(Projections.property("currencyId.currencyName"), "currencyDecs");
		//columns.add(Projections.distinct(Projections.property("currencyId.currencyId")), "currencyId"));
		
		criteria.setProjection(columns);
		criteria.setResultTransformer( new AliasToBeanResultTransformer(CurrencyMasterDTO.class) );
		
		List<CurrencyMasterDTO> lstCurrencyMasterDTO=  (List<CurrencyMasterDTO>) findAll(criteria);
		
		
		return lstCurrencyMasterDTO;
	}

	@Override
	public BigDecimal getBalance(BigDecimal countryBranchId, String userName,BigDecimal currencyId) {
		
		SQLQuery sqlQuery = super.getSession().createSQLQuery("select EX_FN_GET_CURRENCY_BAL(:COUNTRY_BRANCH_ID,:USER_NAME,:CURRENCY_ID) as STATEDISTRICT from Dual");
		sqlQuery.setBigDecimal("COUNTRY_BRANCH_ID", countryBranchId);
		sqlQuery.setString("USER_NAME", userName);
		sqlQuery.setBigDecimal("CURRENCY_ID", currencyId);
		BigDecimal balanceAmt=(BigDecimal) sqlQuery.uniqueResult();
		
		return balanceAmt;
	
	}
	

	@Override
	public List<CurrencyWiseDenomination> currencyWiseDenominations(BigDecimal currencyId){
		DetachedCriteria criteria = DetachedCriteria.forClass(CurrencyWiseDenomination.class, "currencyWiseDenomination");
		
		criteria.setFetchMode("currencyWiseDenomination.exCurrencyMaster", FetchMode.JOIN);
		criteria.createAlias("currencyWiseDenomination.exCurrencyMaster", "exCurrencyMaster",JoinType.INNER_JOIN); 
		criteria.add(Restrictions.eq("exCurrencyMaster.currencyId", currencyId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<CurrencyWiseDenomination>)findAll(criteria);
	}

	
	@Override
	public void saveForeignCurrencyAdjustRecords(List<ForeignCurrencyAdjust> foreignCurrencyAdjList)throws Exception{
		try{
			for(ForeignCurrencyAdjust foreignCurrencyAdjust:foreignCurrencyAdjList){
			getSession().saveOrUpdate(foreignCurrencyAdjust);
			}
		}catch(Exception e){
			log.error("Error Occured While Saving" +e.getMessage());
		}
		
	}
	
}
