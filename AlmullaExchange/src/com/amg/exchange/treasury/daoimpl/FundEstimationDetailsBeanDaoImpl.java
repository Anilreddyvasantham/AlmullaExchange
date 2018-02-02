package com.amg.exchange.treasury.daoimpl;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.remittance.bean.PersonalRemittanceBean;
import com.amg.exchange.treasury.dao.FundEstimationDetailsBeanDao;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.FundEstimationDays;
import com.amg.exchange.treasury.model.FundEstimationDetails;
import com.amg.exchange.util.AMGException;

@Repository
public class FundEstimationDetailsBeanDaoImpl<T> extends CommonDaoImpl<T> implements FundEstimationDetailsBeanDao{

	Logger LOGGER = Logger.getLogger(FundEstimationDetailsBeanDaoImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FundEstimationDetails> getData(BigDecimal countryId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(FundEstimationDetails.class, "fundEstimationDetails");
		
		dCriteria.setFetchMode("fundEstimationDetails.exFundEstimtaionDetailsForApplicationCountry", FetchMode.JOIN);
		dCriteria.createAlias("fundEstimationDetails.exFundEstimtaionDetailsForApplicationCountry", "exFundEstimtaionDetailsForApplicationCountry", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exFundEstimtaionDetailsForApplicationCountry.countryId", countryId));
		
		dCriteria.setFetchMode("fundEstimationDetails.exBankMaster", FetchMode.JOIN);
		dCriteria.createAlias("fundEstimationDetails.exBankMaster", "exBankMaster", JoinType.INNER_JOIN);
		
		dCriteria.setFetchMode("fundEstimationDetails.fundEstimtaionId", FetchMode.JOIN);
		dCriteria.createAlias("fundEstimationDetails.fundEstimtaionId", "fundEstimtaionId", JoinType.INNER_JOIN);
		
		dCriteria.setFetchMode("fundEstimationDetails.exCurrenyMaster", FetchMode.JOIN);
		dCriteria.createAlias("fundEstimationDetails.exCurrenyMaster", "exCurrenyMaster", JoinType.INNER_JOIN);
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<FundEstimationDetails>) findAll(dCriteria);
		
	}
	
	@Override
	public String getCountryName(BigDecimal countryId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CountryMasterDesc.class, "countryMasterDesc");
		
		dCriteria.setFetchMode("countryMasterDesc.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("countryMasterDesc.fsCountryMaster", "fsCountryMaster",  JoinType.INNER_JOIN);
		
		dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return ((CountryMasterDesc) dCriteria.getExecutableCriteria(getSession()).list().get(0)).getCountryName();
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getDecimalValue(BigDecimal currencyId) {
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CurrencyMaster.class, "currencyMaster");
		dCriteria.add(Restrictions.eq("currencyMaster.currencyId", currencyId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CurrencyMaster> lstCurrency = dCriteria.getExecutableCriteria(getSession()).list(); 
		return lstCurrency.get(0).getDecinalNumber().intValue();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public BigDecimal getUsdPk(String currencyCode) {
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CurrencyMaster.class, "currencyMaster");
		dCriteria.add(Restrictions.eq("currencyMaster.currencyCode", currencyCode));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CurrencyMaster> lstCurrency = dCriteria.getExecutableCriteria(getSession()).list(); 
		
		return lstCurrency.get(0).getCurrencyId();
	}

	@Override
	public HashMap<String, String> callFundEstimation(
			HashMap<String, String> inputValues) throws AMGException {
		LOGGER.info("Entered into callFundEstimation(HashMap<String, String> inputValues) Method");
		HashMap<String, String> outputValues = new HashMap<String, String>();
		Connection connection = null;
		try {
			// connection = DBConnection.getdataconnection();
			connection = getDataSourceFromHibernateSession();
		} catch (Exception e) {
			LOGGER.error( "PROBLEM OCCURED WHILE GETTTING CONECTION");
			e.printStackTrace();
		}
		getFundEstimationDyas(inputValues);
		LOGGER.info("!!!!!! callFundEstimation IN PUT VALUES  !!!!!!!!! ==  " + inputValues.toString());
		LOGGER.info("!Calling  EX_P_FUND_ESTIMATION Procedure with INPUT VALUES  !!!!!");
		LOGGER.info(inputValues.toString());
		CallableStatement cs;
		try {
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	        Date parsed = format.parse(inputValues.get("P_PROJECTION_DATE"));
	        java.sql.Date sqlProjectionDate = new java.sql.Date(parsed.getTime());
	        
			String call = " { call EX_P_FUND_ESTIMATION (?,?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?) } ";
			cs = connection.prepareCall(call);
			cs.setBigDecimal(1, new BigDecimal(inputValues.get("P_APPLICATION_COUNTRY_ID")));
			cs.setBigDecimal(2, new BigDecimal(inputValues.get("P_COMPANY_ID")));
			cs.setBigDecimal(3, new BigDecimal(inputValues.get("P_BANK_COUNTRY_ID")));
			cs.setBigDecimal(4, new BigDecimal(inputValues.get("P_CURRENCY_ID")));
			cs.setBigDecimal(5, new BigDecimal(inputValues.get("P_BANK_ID")));
			cs.setBigDecimal(6, new BigDecimal(inputValues.get("P_SERVICE_ID")));
			cs.setDate(7, sqlProjectionDate);
		
			cs.registerOutParameter(8, java.sql.Types.DATE);
			cs.registerOutParameter(9, java.sql.Types.DATE);
			cs.registerOutParameter(10, java.sql.Types.DATE);
			cs.registerOutParameter(11, java.sql.Types.DATE);
			cs.registerOutParameter(12, java.sql.Types.DATE);
			cs.registerOutParameter(13, java.sql.Types.DATE);
			cs.registerOutParameter(14, java.sql.Types.INTEGER);
			cs.registerOutParameter(15, java.sql.Types.INTEGER);
			cs.registerOutParameter(16, java.sql.Types.INTEGER);
			cs.registerOutParameter(17, java.sql.Types.INTEGER);
			cs.registerOutParameter(18, java.sql.Types.INTEGER);
			cs.registerOutParameter(19, java.sql.Types.INTEGER);
			
			cs.execute();
			outputValues.put("P_PREVIOUS_MONTH_DT1", cs.getDate(8) == null ? "0" : format.format(cs.getDate(8)));
			outputValues.put("P_PREVIOUS_MONTH_DT2", cs.getDate(9) == null ? "0" : format.format(cs.getDate(9)));
			outputValues.put("P_PREVIOUS_MONTH_DT3", cs.getDate(10) == null ? "0" : format.format(cs.getDate(10)));
			outputValues.put("P_PREVIOUS_WEEK_DT1", cs.getDate(11) == null ? "0" : format.format(cs.getDate(11)));
			outputValues.put("P_PREVIOUS_WEEK_DT2", cs.getDate(12) == null ? "0" : format.format(cs.getDate(12)));
			outputValues.put("P_PREVIOUS_WEEK_DT3", cs.getDate(13) == null ? "0" : format.format(cs.getDate(13)));
			outputValues.put("P_PREVIOUS_MONTH_1", cs.getBigDecimal(14) == null ? "0" : cs.getBigDecimal(14).toPlainString());
			outputValues.put("P_PREVIOUS_MONTH_2", cs.getBigDecimal(15) == null ? "0" : cs.getBigDecimal(15).toPlainString());
			outputValues.put("P_PREVIOUS_MONTH_3", cs.getBigDecimal(16) == null ? "0" : cs.getBigDecimal(16).toPlainString());
			outputValues.put("P_PREVIOUS_WEEK_1", cs.getBigDecimal(17) == null ? "0" : cs.getBigDecimal(17).toPlainString());
			outputValues.put("P_PREVIOUS_WEEK_2", cs.getBigDecimal(18) == null ? "0" : cs.getBigDecimal(18).toPlainString());
			outputValues.put("P_PREVIOUS_WEEK_3", cs.getBigDecimal(19) == null ? "0" : cs.getBigDecimal(19).toPlainString());
			
			System.out.println("!!!!!! callFundEstimation outputValues VALUES  !!!!!!!!! ==  " + outputValues.toString());
			LOGGER.info("!After Calling  EX_P_FUND_ESTIMATION Procedure returns OUTPUT VALUES  !!!!!");
			LOGGER.info(outputValues.toString());
		} catch (SQLException e) {
			LOGGER.error( "Error While Procedure Calling "+ e.getMessage());
			String erromsg = "EX_P_FUND_ESTIMATION" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.error( "Error While Procedure Calling "+ e.getMessage());
				String erromsg = "EX_P_FUND_ESTIMATION" + " : " + e.getMessage();
				throw new AMGException(erromsg);
			}
		}
		return outputValues;
	}

	public List<FundEstimationDays> getFundEstimationDyas(HashMap<String, String> inputValues) throws AMGException
	{			
		DetachedCriteria dCriteria = DetachedCriteria.forClass(FundEstimationDays.class, "fundEstimationDays");
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		List<FundEstimationDays> lstFundEstimationDays =null;
		try {
			Date parsed = format.parse(inputValues.get("P_PROJECTION_DATE"));
			
			dCriteria.add(Restrictions.eq("fundEstimationDays.projectionDate", parsed));
			dCriteria.add(Restrictions.eq("fundEstimationDays.applicationCountryId", new BigDecimal(inputValues.get("P_APPLICATION_COUNTRY_ID"))));
			dCriteria.add(Restrictions.eq("fundEstimationDays.bankCountryId", new BigDecimal(inputValues.get("P_BANK_COUNTRY_ID"))));
			dCriteria.add(Restrictions.eq("fundEstimationDays.currencyId", new BigDecimal(inputValues.get("P_CURRENCY_ID"))));
			dCriteria.add(Restrictions.eq("fundEstimationDays.bankId", new BigDecimal(inputValues.get("P_BANK_ID"))));
			dCriteria.add(Restrictions.eq("fundEstimationDays.serviceId", new BigDecimal(inputValues.get("P_SERVICE_ID"))));
			
			dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			lstFundEstimationDays = dCriteria.getExecutableCriteria(getSession()).list(); 
		} catch (ParseException e) {
			LOGGER.error( "Error While getFundEstimationDyas Calling "+ e.getMessage());
			String erromsg = "getFundEstimationDyas" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		}
		
		return lstFundEstimationDays;
	}

}
