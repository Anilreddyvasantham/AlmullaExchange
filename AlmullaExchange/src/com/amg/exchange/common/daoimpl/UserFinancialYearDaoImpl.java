package com.amg.exchange.common.daoimpl;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.bean.UserFinancialYearBean;
import com.amg.exchange.common.dao.IUserFinancialYearDao;
import com.amg.exchange.common.model.StateMasterDesc;
import com.amg.exchange.foreigncurrency.model.Collect;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.treasury.model.RemittanceModeMaster;
import com.amg.exchange.util.AMGException;

@SuppressWarnings({ "unchecked", "rawtypes" })
@Repository
public class UserFinancialYearDaoImpl extends CommonDaoImpl implements IUserFinancialYearDao {
	
	private static final Logger LOGGER = Logger.getLogger(UserFinancialYearBean.class);
	
	@Override
	public Hashtable<String,Date> getFinancialDatevalues(BigDecimal financialYear) throws AMGException {
		LOGGER.info("Entered into getFinancialDatevalues()  Method Called ");
		LOGGER.info("Procedure Name =  EX_P_GET_FINANCIAL_DATE ");
		LOGGER.info("getFinancialDatevalues() Method Called ");
		LOGGER.info("!!!!!!appcountryId!!!!!!!!!" + financialYear);
		
		Hashtable<String,Date> table =  new Hashtable<String,Date>();
		Connection connection = null;
		try {
			// connection = DBConnection.getdataconnection();
			connection = getDataSourceFromHibernateSession();
		} catch (Exception e) {
			e.printStackTrace();
		}
		CallableStatement cs;
		try {
			String call = " { call EX_P_GET_FINANCIAL_DATE (?, ?, ?) } ";
			cs = connection.prepareCall(call);
			cs.setBigDecimal(1, financialYear);
			
			cs.registerOutParameter(2, java.sql.Types.DATE);
			cs.registerOutParameter(3, java.sql.Types.DATE);
			
			cs.execute();// teUpdate();
			Date out1 = cs.getDate(2);
			Date out2 = cs.getDate(3);
			
			/*outLst.add(out1);
			outLst.add(out2);*/
			
			table.put("startDate", out1);
			table.put("endDate", out2);
			
		} catch (SQLException e) {
			LOGGER.info("Problem Occured When Procedure Calling="+e.getMessage());
			String erromsg = "EX_P_GET_FINANCIAL_DATE" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.info("Problem Occured When Procedure Calling="+e.getMessage());
				String erromsg = "EX_P_GET_FINANCIAL_DATE" + " : " + e.getMessage();
				throw new AMGException(erromsg);
			}
		}
		LOGGER.info("!!!!!!outLst out!!!!!!!!!" + table);
		LOGGER.info("Exited from the getExchangeRateAllValues()  Method ");
		return table;
	}
	
	@Override
	public List<BigDecimal> getUserFinancialYear(BigDecimal financeYear) {

		List<BigDecimal> data = new ArrayList<BigDecimal>();
		String queryString = "select FINANCIAL_YEAR  from EX_USER_FINANCIAL_YEAR this_  where FINANCIAL_YEAR like '" + financeYear + "%'";
		data = getSession().createSQLQuery(queryString).list();

		if (data.isEmpty())
			return null;

		return data;

	}
	@Override
	public List<UserFinancialYear> getUserFinancialYearList(){
		
		DetachedCriteria criteria = DetachedCriteria.forClass(UserFinancialYear.class, "userFinancialYear");
		
		criteria.addOrder(Order.desc("userFinancialYear.financialYear"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<UserFinancialYear>)findAll(criteria);
		
	}
	
	@Override
	public Boolean isExistUserFinancialYear(BigDecimal financeYear){

		DetachedCriteria criteria = DetachedCriteria.forClass(UserFinancialYear.class, "userFinancialYear");
		criteria.add(Restrictions.eq("userFinancialYear.financialYear", financeYear));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<UserFinancialYear> data = (List<UserFinancialYear>)findAll(criteria);
		if (data.isEmpty())
			return false;
		

		return true;
	}
	
	@Override
	public void save(UserFinancialYear userFinancialYear) {
		getSession().saveOrUpdate(userFinancialYear);
	}


}
