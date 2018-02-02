package com.amg.exchange.treasury.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.treasury.dao.IBankApplicabilityDao;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankIndicator;
import com.amg.exchange.treasury.model.BankIndicatorDescription;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;

@SuppressWarnings({"unused", "unchecked"})
@Repository
public class BankApplicabilityDaoImpl<T> extends CommonDaoImpl<T> implements
		IBankApplicabilityDao<T>, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(BankApplicabilityDaoImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public void saveBankApplicabilityDetails(BankApplicability bankApplicability) {
		getSession().saveOrUpdate(bankApplicability);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankIndicator> getBankIndicatorList() {
		DetachedCriteria criteria = DetachedCriteria.forClass(
				BankIndicator.class, "bankIndicator");
       //start by subramanian for asc order bankIndicatorName 
		criteria.addOrder(Order.asc("bankIndicator.bankIndicatorName"));
		// End by subramanian for asc order bankIndicatorName 
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<BankIndicator>) findAll(criteria);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankApplicability> getBankApplicability(BigDecimal companyId,
			BigDecimal countryId, BigDecimal bankTypeId, BigDecimal bankId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BankApplicability.class, "bankApplicability");
		

		criteria.setFetchMode("bankApplicability.fsCompanyMaster", FetchMode.JOIN);
		criteria.createAlias("bankApplicability.fsCompanyMaster", "fsCompanyMaster", JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("fsCompanyMaster.companyId", companyId));
		//to blocked koti@26/03/2015 we are getting web user name to deacivate time
		//criteria.add(Restrictions.eq("bankApplicability.isActive", "Y"));
		
		criteria.setFetchMode("bankApplicability.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("bankApplicability.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		
		criteria.setFetchMode("bankApplicability.bankMaster", FetchMode.JOIN);
		criteria.createAlias("bankApplicability.bankMaster", "bankMaster", JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("bankMaster.bankId", bankId));
		
		criteria.setFetchMode("bankApplicability.bankInd", FetchMode.JOIN);
		criteria.createAlias("bankApplicability.bankInd", "bankInd", JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("bankInd.bankIndicatorId", bankTypeId));

		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		

		return (List<BankApplicability>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankMaster> getBanklist(BigDecimal bankId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BankMaster.class, "bankMaster");
	
		
		criteria.add(Restrictions.eq("bankMaster.bankId", bankId));
		criteria.add(Restrictions.eq("bankMaster.fileAlls", "W"));
	
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		return (List<BankMaster>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankApplicability> getBankApplicabilityForView(
			BigDecimal companyId, BigDecimal countryId, BigDecimal bankId) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(BankApplicability.class, "bankApplicability");
		
		criteria.setFetchMode("bankApplicability.fsCompanyMaster", FetchMode.JOIN);
		criteria.createAlias("bankApplicability.fsCompanyMaster", "fsCompanyMaster", JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("fsCompanyMaster.companyId", companyId));
		
		// CR Done Due to Fetch All Records
		//criteria.add(Restrictions.disjunction()
			//.add(Restrictions.eq("bankApplicability.isActive", Constants.U))
			//.add(Restrictions.eq("bankApplicability.isActive", Constants.D)));
		
		criteria.setFetchMode("bankApplicability.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("bankApplicability.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		
		criteria.setFetchMode("bankApplicability.bankMaster", FetchMode.JOIN);
		criteria.createAlias("bankApplicability.bankMaster", "bankMaster", JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("bankMaster.bankId", bankId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		
		return (List<BankApplicability>) findAll(criteria);
	}

	@Override
	public List<CountryMasterDesc> getCountryFromCompany(BigDecimal companyId,
			BigDecimal languageId) {
		
		
		DetachedCriteria criteria = DetachedCriteria.forClass(CompanyMasterDesc.class, "companyMasterDesc");
		
		criteria.setFetchMode("companyMasterDesc.fsCompanyMaster", FetchMode.JOIN);
		criteria.createAlias("companyMasterDesc.fsCompanyMaster", "fsCompanyMaster", JoinType.INNER_JOIN);
		
		criteria.setFetchMode("fsCompanyMaster.countryMaster", FetchMode.JOIN);
		criteria.createAlias("fsCompanyMaster.countryMaster", "countryMaster", JoinType.INNER_JOIN);
		
		

		criteria.setFetchMode("companyMasterDesc.fsLanguageType", FetchMode.JOIN);
		criteria.createAlias("companyMasterDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));
		
		criteria.add(Restrictions.eq("fsCompanyMaster.companyId", companyId));
		
		criteria.addOrder(Order.asc("companyMasterDesc.companyName"));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<CompanyMasterDesc> lstCompanyMasterDesc= (List<CompanyMasterDesc>)findAll(criteria);
		
		List<CountryMasterDesc> lstCountryMasterDesc= new ArrayList<CountryMasterDesc>();
		
		for(CompanyMasterDesc companyMasterDesc :lstCompanyMasterDesc)
		{
			
			BigDecimal countryId=companyMasterDesc.getFsCompanyMaster().getCountryMaster().getCountryId();
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CountryMasterDesc.class,"countryMasterDesc");
			
			// Join Language Type table
			detachedCriteria.setFetchMode("countryMasterDesc.fsLanguageType", FetchMode.JOIN);
			detachedCriteria.createAlias("countryMasterDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
			
			// Add Language Condition
			detachedCriteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));
			
			// Add Bussness of country Condition
			detachedCriteria.add(Restrictions.eq("fsCountryMaster.businessCountry", Constants.Yes));
					
			// Join Country Master Table
			detachedCriteria.setFetchMode("countryMasterDesc.fsCountryMaster", FetchMode.JOIN);
			detachedCriteria.createAlias("countryMasterDesc.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
			// start by subramanian for asc countryName
			detachedCriteria.addOrder(Order.asc("countryMasterDesc.countryName"));
			// End by subramanian for asc countryName
			
			detachedCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
			
			detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			
			lstCountryMasterDesc=(List<CountryMasterDesc>) findAll(detachedCriteria);
		}
		
		
		
		return lstCountryMasterDesc;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankApplicability> getBankApplicabilityApproval(
			BigDecimal bankApplicabilityId) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(BankApplicability.class, "bankApplicability");

		criteria.setFetchMode("bankApplicability.fsCompanyMaster", FetchMode.JOIN);
		criteria.createAlias("bankApplicability.fsCompanyMaster", "fsCompanyMaster", JoinType.LEFT_OUTER_JOIN);
		
		//criteria.add(Restrictions.eq("bankApplicability.isActive", "U"));
		
		criteria.setFetchMode("bankApplicability.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("bankApplicability.fsCountryMaster", "fsCountryMaster", JoinType.LEFT_OUTER_JOIN);
		
		
		criteria.setFetchMode("bankApplicability.bankMaster", FetchMode.JOIN);
		criteria.createAlias("bankApplicability.bankMaster", "bankMaster", JoinType.LEFT_OUTER_JOIN);
		
		criteria.setFetchMode("bankApplicability.bankInd", FetchMode.JOIN);
		criteria.createAlias("bankApplicability.bankInd", "bankInd", JoinType.LEFT_OUTER_JOIN);
		
		criteria.add(Restrictions.eq("bankApplicability.applicabilityId", bankApplicabilityId));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		

		return (List<BankApplicability>) findAll(criteria);
	}

	@Override
	public void activeDeactiveRecord(BigDecimal bankApplicabilityID, String status,String remarks) {
		
		SessionStateManage sessionStateManage = new SessionStateManage();
		DetachedCriteria criteria = DetachedCriteria.forClass(BankApplicability.class, "bankApplicability");
		criteria.add(Restrictions.eq("bankApplicability.applicabilityId", bankApplicabilityID));
		List<BankApplicability> lstBankApplicability=(List<BankApplicability>) findAll(criteria);
		
		if(lstBankApplicability.size()>0)
		{
			BankApplicability bankApplicability=lstBankApplicability.get(0);
			if(status.equalsIgnoreCase("Y"))
			{
				bankApplicability.setIsActive(Constants.D);
				bankApplicability.setRemarks(remarks);
				bankApplicability.setUpdateDate(new Date());
				bankApplicability.setModifier(sessionStateManage.getUserName());
			}
			if(status.equalsIgnoreCase("D"))
			{
				bankApplicability.setIsActive(Constants.U);
				bankApplicability.setUpdateDate(new Date());
				bankApplicability.setModifier(sessionStateManage.getUserName());
				bankApplicability.setRemarks(null);
				bankApplicability.setApprovedBy(null);
				bankApplicability.setApprovedDate(null);
			}
			getSession().saveOrUpdate(bankApplicability);
		}
	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankIndicatorDescription> getBankIndicatorDescList(BigDecimal languageId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BankIndicatorDescription.class, "bankIndicatorDesc");
		
		criteria.setFetchMode("bankIndicatorDesc.languageType",FetchMode.JOIN);
		criteria.createAlias("bankIndicatorDesc.languageType","languageType", JoinType.INNER_JOIN);
		criteria.setFetchMode("bankIndicatorDesc.bankIndicator", FetchMode.JOIN);
		criteria.createAlias("bankIndicatorDesc.bankIndicator", "bankIndicator",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("bankIndicator.isActive",  Constants.Yes));
		criteria.add(Restrictions.eq("languageType.languageId", languageId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		return (List<BankIndicatorDescription>) findAll(criteria);

	}

	
	@Override
	public List<BigDecimal> getCorrespondingCountryList(BigDecimal appCountryId,String cB) {
		
		System.out.println("appCountryId" + appCountryId);
		System.out.println("cB " + cB);
		LOGGER.info("Entering into getCorrespondingCountryList method");
		DetachedCriteria criteria = DetachedCriteria.forClass(BankApplicability.class, "bankApplicability");
		criteria.setFetchMode("bankApplicability.fsCompanyMaster", FetchMode.JOIN);
		criteria.createAlias("bankApplicability.fsCompanyMaster", "fsCompanyMaster", JoinType.INNER_JOIN);
		criteria.setFetchMode("bankApplicability.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("bankApplicability.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCountryMaster.countryId", appCountryId));
		criteria.setFetchMode("bankApplicability.bankMaster", FetchMode.JOIN);
		criteria.createAlias("bankApplicability.bankMaster", "bankMaster", JoinType.INNER_JOIN);
		criteria.setFetchMode("bankApplicability.bankInd", FetchMode.JOIN);
		criteria.createAlias("bankApplicability.bankInd", "bd", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("bd.bankIndicatorCode", cB));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BankApplicability> list = (List<BankApplicability>) findAll(criteria);
		Set<BigDecimal> dup = new HashSet<BigDecimal>();
		if (list != null && !list.isEmpty()) {
			for (BankApplicability app : list) {
				dup.add(app.getBankMaster().getFsCountryMaster().getCountryId());
			}
			List<BigDecimal> finalList = new ArrayList<BigDecimal>();
			finalList.addAll(dup);
			return finalList;
		}
		LOGGER.info("Exit into getCorrespondingCountryList method");
		return null;
	}

	@Override
	public List<BankApplicability> getBankList(BigDecimal appcountryId,String bankIndicatorCorrBank, BigDecimal countryId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BankApplicability.class, "bankApplicability");
		criteria.setFetchMode("bankApplicability.fsCompanyMaster", FetchMode.JOIN);
		criteria.createAlias("bankApplicability.fsCompanyMaster", "fsCompanyMaster", JoinType.INNER_JOIN);
		criteria.setFetchMode("bankApplicability.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("bankApplicability.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCountryMaster.countryId", appcountryId));
		criteria.setFetchMode("bankApplicability.bankMaster", FetchMode.JOIN);
		criteria.createAlias("bankApplicability.bankMaster", "bankMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("bankMaster.fsCountryMaster", countryId));
		criteria.setFetchMode("bankApplicability.bankInd", FetchMode.JOIN);
		criteria.createAlias("bankApplicability.bankInd", "bd", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("bd.bankIndicatorCode", bankIndicatorCorrBank));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BankApplicability> list = (List<BankApplicability>) findAll(criteria);
		return (List<BankApplicability>) findAll(criteria);
	}

	@Override
	public List<BankApplicability> getApplicabilityBankList(BigDecimal countryId, String bankIndicatorCorrBank) {
		System.out.println("countryId " + countryId);
		System.out.println("bankIndicatorCorrBank " + bankIndicatorCorrBank);
		LOGGER.info("Entering into getCorrespondingCountryList method");
		DetachedCriteria criteria = DetachedCriteria.forClass(BankApplicability.class, "bankApplicability");
		criteria.setFetchMode("bankApplicability.fsCompanyMaster", FetchMode.JOIN);
		criteria.createAlias("bankApplicability.fsCompanyMaster", "fsCompanyMaster", JoinType.INNER_JOIN);
		criteria.setFetchMode("bankApplicability.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("bankApplicability.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		criteria.setFetchMode("bankApplicability.bankMaster", FetchMode.JOIN);
		criteria.createAlias("bankApplicability.bankMaster", "bankMaster", JoinType.INNER_JOIN);
		criteria.setFetchMode("bankApplicability.bankInd", FetchMode.JOIN);
		criteria.createAlias("bankApplicability.bankInd", "bd", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("bd.bankIndicatorCode", bankIndicatorCorrBank));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<BankApplicability>) findAll(criteria);
	}

	@Override
	public List<BankApplicability> getApplicabilityBankingChennalBankList(BigDecimal countryId, String bankIndicatorAgentBank) {

		System.out.println("countryId " + countryId);
		System.out.println("bankIndicatorCorrBank " + bankIndicatorAgentBank);
		LOGGER.info("Entering into getCorrespondingCountryList method");
		DetachedCriteria criteria = DetachedCriteria.forClass(BankApplicability.class, "bankApplicability");
		criteria.setFetchMode("bankApplicability.fsCompanyMaster", FetchMode.JOIN);
		criteria.createAlias("bankApplicability.fsCompanyMaster", "fsCompanyMaster", JoinType.INNER_JOIN);
		criteria.setFetchMode("bankApplicability.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("bankApplicability.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		criteria.setFetchMode("bankApplicability.bankMaster", FetchMode.JOIN);
		criteria.createAlias("bankApplicability.bankMaster", "bankMaster", JoinType.INNER_JOIN);
		criteria.setFetchMode("bankApplicability.bankInd", FetchMode.JOIN);
		criteria.createAlias("bankApplicability.bankInd", "bd", JoinType.INNER_JOIN);
		criteria.add(Restrictions.ne("bd.bankIndicatorCode", bankIndicatorAgentBank));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<BankApplicability>) findAll(criteria);
	
	}

	@Override
	public HashMap<String, String> callPopulateBankApplicability(HashMap<String, String> inputValues) throws AMGException {
		HashMap<String, String> outputValues = new HashMap<String, String>();
		Connection connection = null;
		String errString = null;
		
		try {
			connection = getDataSourceFromHibernateSession();
			CallableStatement cs;
			String call = " {call EX_P_POPULATE_BANK_INDICATORS  (?,?,?) } ";
			cs = connection.prepareCall(call);
	 		
			cs.setBigDecimal(1, new BigDecimal(inputValues.get("BANK_ID")));
			cs.setString(2, inputValues.get("BANK_CODE"));
			cs.registerOutParameter(3, java.sql.Types.VARCHAR);
			cs.execute();
			errString = cs.getString(3);
 			outputValues.put("P_ERROR_MESSAGE", errString);
 			
		} catch (SQLException e) {
			String erromsg = "EX_P_POPULATE_BANK_INDICATORS" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				String erromsg = "EX_P_POPULATE_BANK_INDICATORS" + " : " + e.getMessage();
				throw new AMGException(erromsg);
			}
		}
		
		return outputValues;
	 
	}

}
