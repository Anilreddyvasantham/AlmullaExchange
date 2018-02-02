package com.amg.exchange.registration.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.registration.dao.ICompanyMastereDao;
import com.amg.exchange.remittance.model.ViewBeneServiceCurrency;
import com.amg.exchange.util.Constants;

@SuppressWarnings("unchecked")
@Repository
public class CompanyMasterDaoImpl<T> extends CommonDaoImpl<T> implements ICompanyMastereDao, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(CompanyMasterDaoImpl.class);

	@Override
	public void save(CountryMaster countryMaster) {
		System.out.println("Entering into save method");
		getSession().saveOrUpdate(countryMaster);
		System.out.println("Exit into save method");
	}
	
	public void save(CompanyMaster companyMaster) {
		System.out.println("Entering into save method");
		getSession().saveOrUpdate(companyMaster);
		System.out.println("Exit into save method");
	}	
	
	public void save(CompanyMasterDesc companyMasterDesc) {
		System.out.println("Entering into save method");
		getSession().saveOrUpdate(companyMasterDesc);
		System.out.println("Exit into save method");
	}

	@Override
	public List<String> getAllComponent(String query) {
		String queryString = "select company_code from fs_company_master where company_code like '%" + query + "%'";
		return getSession().createSQLQuery(queryString).list();
	}

	@Override
	public void saveDescription(CountryMasterDesc countryeMasterDesc) {
		
	}

	@Override
	public CompanyMaster viewByCode(BigDecimal companyCode) {
		LOGGER.info("Entering into viewByCode method");
		LOGGER.info("articleCode -" +companyCode);
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CompanyMaster.class, "companyMaster");
		dCriteria.add(Restrictions.eq("companyMaster.companyCode", companyCode));
		List<CompanyMaster> list=(List<CompanyMaster>) findAll(dCriteria);
		
		if(list.isEmpty()) {
			LOGGER.info("Company code not found");
			return null;
			
		}
		LOGGER.info("Exit into viewByCode method");
		return list.get(0);
	}

	@Override
	public List<CompanyMasterDesc> checkDesciption(String localcompanyDescription) {
		return null;
	}

	@Override
	public List<CompanyMasterDesc> viewById(BigDecimal companyId) {
		LOGGER.info("Entering into viewById method");
		LOGGER.info("companyId -" +companyId);
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CompanyMasterDesc.class, "companyMasterDesc");
		dCriteria.setFetchMode("companyMasterDesc.fsCompanyMaster", FetchMode.JOIN);
		dCriteria.createAlias("companyMasterDesc.fsCompanyMaster", "companyMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("companyMaster.companyId", companyId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exit into viewById method");
		return (List<CompanyMasterDesc>) findAll(dCriteria);
	}

	@Override
	public void save(CompanyMaster savecompanyMaster, List<CompanyMasterDesc> list) throws Exception{
	try {
		save(savecompanyMaster);
		for (CompanyMasterDesc companyMasterDesc : list) {
			companyMasterDesc.setFsCompanyMaster(savecompanyMaster);
			save(companyMasterDesc);
		}
		
	} catch (HibernateException e) {
		throw e;
	}
		
	}
	

	@Override
	public List<CompanyMaster> viewMasterRecords() {

		LOGGER.info("Entering into viewMasterRecords method");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CompanyMaster.class, "companyMaster");
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exit into viewMasterRecords method");
		return (List<CompanyMaster>) findAll(detachedCriteria);
	
		
	}	
	
	
	public void delete(CompanyMaster companyMaster) {
		LOGGER.info("Entering into delete method");
		getSession().delete(companyMaster);
		LOGGER.info("Exit into delete method");
	}

	public void deleteDesc(CompanyMasterDesc companyMasterDesc) {
		LOGGER.info("Entering into deleteDesc method");
		getSession().delete(companyMasterDesc);
		LOGGER.info("Exit into deleteDesc method");
		
	}
	
	

	@Override
	public void delete(CompanyMaster companyMaster, List<CompanyMasterDesc> desc) {

		
		for (CompanyMasterDesc companyMasterDesc : desc) {
			
			deleteDesc(companyMasterDesc);
		}
		
		delete(companyMaster);

	}

	@Override
	public List<CompanyMaster> viewMasterRecordsforApproval() {

		LOGGER.info("Entering into viewMasterRecordsforApproval method");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CompanyMaster.class, "companyMaster");
		detachedCriteria.add(Restrictions.eq("companyMaster.isActive", Constants.U));
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exit into viewMasterRecordsforApproval method");
		return (List<CompanyMaster>) findAll(detachedCriteria);
	
		
	}

	@Override
	public void approveRecord(BigDecimal companyId, String userName) {

		CompanyMaster companyMaster=(CompanyMaster) getSession().get(CompanyMaster.class, companyId);
		companyMaster.setIsActive(Constants.Yes);
		companyMaster.setApprovedBy(userName);
		companyMaster.setApprovedDate(new Date());
		getSession().update(companyMaster);
	
	}

	@Override
	public String viewById(BigDecimal companyId, BigDecimal languageId) {
		LOGGER.info("Entering into viewById method");
		LOGGER.info("companyId -" +companyId);
		LOGGER.info("languageId -" +languageId);
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CompanyMasterDesc.class, "companyMasterDesc");
		dCriteria.setFetchMode("companyMasterDesc.fsCompanyMaster", FetchMode.JOIN);
		dCriteria.createAlias("companyMasterDesc.fsCompanyMaster", "companyMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("companyMaster.companyId", companyId));
		
		dCriteria.setFetchMode("companyMasterDesc.fsLanguageType", FetchMode.JOIN);
		dCriteria.createAlias("companyMasterDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exit into viewById method");
		List<CompanyMasterDesc> list = (List<CompanyMasterDesc>) findAll(dCriteria);
		
		if(list!=null && list.size()!=0)
		{
			return list.get(0).getCompanyName();
		}
		
		return null;
	}
	
	@Override
	public void saveCompanyValues(CompanyMaster companyMaster,List<CompanyMasterDesc> comMasterDesc) throws Exception{
		try{
			saveComMaster(companyMaster);
			for (CompanyMasterDesc companyMasterDesc : comMasterDesc) {
				companyMasterDesc.setFsCompanyMaster(companyMaster);
				saveComMasterDesc(companyMasterDesc);
			}
		}catch (HibernateException e) {
			throw e;
		}
			
	}
	
	
	public void saveComMaster(CompanyMaster companyMaster) {		
		getSession().saveOrUpdate(companyMaster);		
	}
	
	public void saveComMasterDesc(CompanyMasterDesc companyMasterDesc) {
		getSession().saveOrUpdate(companyMasterDesc);
	}
	
}
