package com.amg.exchange.registration.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.registration.dao.ICountryMastereDao;
import com.amg.exchange.util.Constants;

@SuppressWarnings("unchecked")
@Repository
public class CountryMasterDaoImpl<T> extends CommonDaoImpl<T> implements ICountryMastereDao, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(CountryMasterDaoImpl.class);

	@Override
	public void save(CountryMaster countryMaster) {
		LOGGER.info("Entering into save method");
		getSession().saveOrUpdate(countryMaster);
		LOGGER.info("Exit into save method");
	}

	@Override
	public List<CountryMasterDesc> getAllComponent(String query, BigDecimal languageId) {
		LOGGER.info("Entering into getAllComponent method");
		LOGGER.info("query -" + query);
		LOGGER.info("countryId -" + languageId);
		DetachedCriteria criteria = DetachedCriteria.forClass(CountryMasterDesc.class, "countryMasterDesc");
		criteria.setFetchMode("countryMasterDesc.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("countryMasterDesc.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.like("fsCountryMaster.countryCode", query, MatchMode.ANYWHERE).ignoreCase());
		criteria.addOrder(Order.asc("fsCountryMaster.countryCode"));
		criteria.setFetchMode("countryMasterDesc.fsLanguageType", FetchMode.JOIN);
		criteria.createAlias("countryMasterDesc.fsLanguageType", "languageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageType.languageId", languageId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exit into getAllComponent method");
		return (List<CountryMasterDesc>) findAll(criteria);
	}

	@Override
	public void saveDescription(CountryMasterDesc countryeMasterDesc) {
		getSession().saveOrUpdate(countryeMasterDesc);
		
	}

	@Override
	public CountryMaster viewByCode(String countryCode) {
		LOGGER.info("Entering into viewByCode method");
		LOGGER.info("Entering into viewById method");
		LOGGER.info("countryCode - " + countryCode);
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CountryMaster.class, "countryMaster");
		dCriteria.add(Restrictions.eq("countryMaster.countryCode", countryCode));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exit into viewById method");
		List<CountryMaster> list = (List<CountryMaster>) findAll(dCriteria);
		if (list.isEmpty()) {
			LOGGER.info("countryCode not found");
			return null;
		}
		LOGGER.info("Exit into viewByCode method");
		return list.get(0);
	}

	@Override
	public List<CountryMasterDesc> viewDescriptionsById(BigDecimal countryId) {
		LOGGER.info("Entering into viewDescriptionsById method");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CountryMasterDesc.class, "countryMasterDesc");
		detachedCriteria.setFetchMode("countryMasterDesc.fsLanguageType", FetchMode.JOIN);
		detachedCriteria.createAlias("countryMasterDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		detachedCriteria.setFetchMode("countryMasterDesc.fsCountryMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("countryMasterDesc.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		detachedCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exit into viewDescriptionsById method");
		return (List<CountryMasterDesc>) findAll(detachedCriteria);
	}



	@Override
	public List<CountryMaster> getCountryMasterList() {
		LOGGER.info("Entering into getCountryMasterList method");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CountryMaster.class, "countryMaster");
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		detachedCriteria.addOrder(Order.asc("countryMaster.countryId"));
		LOGGER.info("Exit into getCountryMasterList method");
		return (List<CountryMaster>) findAll(detachedCriteria);
	}

	@Override
	public List<CountryMasterDesc> getCountryList() {
		LOGGER.info("Entering into getCountryList method");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CountryMasterDesc.class, "countryMasterDesc");
		detachedCriteria.setFetchMode("countryMasterDesc.fsLanguageType", FetchMode.JOIN);
		detachedCriteria.createAlias("countryMasterDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		detachedCriteria.setFetchMode("countryMasterDesc.fsCountryMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("countryMasterDesc.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		detachedCriteria.addOrder(Order.asc("fsCountryMaster.countryId"));
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exit into getCountryList method");
		return (List<CountryMasterDesc>) findAll(detachedCriteria);
	}

	@Override
	public boolean checkDesciption(String descption) {
		LOGGER.info("Entering into checkDesciption method");
		LOGGER.info("Description -" + descption);
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CountryMasterDesc.class, "countryMasterDesc");
		detachedCriteria.add(Restrictions.eq("countryMasterDesc.countryName", descption));
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exit into checkDesciption method");
		List<CountryMasterDesc> l = (List<CountryMasterDesc>) findAll(detachedCriteria);
		if (l.isEmpty())
			return false;
		return true;
	}

	@Override
	public boolean checkNationality(String nationality) {
		LOGGER.info("Entering into checkNationality method");
		LOGGER.info("Nationality -" +nationality);
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CountryMasterDesc.class, "countryMasterDesc");
		detachedCriteria.add(Restrictions.eq("countryMasterDesc.nationality", nationality));
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exit into checkNationality method");
		List<CountryMasterDesc> l = (List<CountryMasterDesc>) findAll(detachedCriteria);
		if (l.isEmpty())
			return false;
		return true;
	}

	@Override
	public boolean isoCheck(String isoCode) {
		LOGGER.info("Entering into isoCheck method");
		LOGGER.info("Nationality -" +isoCode);
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CountryMaster.class, "countryMaster");
		detachedCriteria.add(Restrictions.eq("countryMaster.countryIsoCode", isoCode));
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exit into isoCheck method");
		List<CountryMaster> l = (List<CountryMaster>) findAll(detachedCriteria);
		if (l.isEmpty())
			return false;
		return true;
	}

	@Override
	public boolean alpha2Check(String countryAlpha2Code) {
		LOGGER.info("Entering into alpha2Check method");
		LOGGER.info("countryAlpha2Code -" +countryAlpha2Code);
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CountryMaster.class, "countryMaster");
		detachedCriteria.add(Restrictions.eq("countryMaster.countryAlpha2Code", countryAlpha2Code));
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exit into alpha2Check method");
		List<CountryMaster> l = (List<CountryMaster>) findAll(detachedCriteria);
		if (l.isEmpty())
			return false;
		return true;
	}

	@Override
	public boolean alpha3Check(String countryAlpha3Code) {
		LOGGER.info("Entering into alpha3Check method");
		LOGGER.info("countryAlpha3Code -" +countryAlpha3Code);
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CountryMaster.class, "countryMaster");
		detachedCriteria.add(Restrictions.eq("countryMaster.countryAlpha3Code", countryAlpha3Code));
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exit into alpha3Check method");
		List<CountryMaster> l = (List<CountryMaster>) findAll(detachedCriteria);
		if (l.isEmpty())
			return false;
		return true;
	}

	@Override
	public void save(HashMap<String, Object> saveObject) throws Exception {
		LOGGER.info("Entering into save method");
		
		
		CopyOnWriteArrayList<CountryMaster> saveMasterList = (CopyOnWriteArrayList<CountryMaster>) saveObject.get("master");
		CopyOnWriteArrayList<CountryMasterDesc> saveDescrList = (CopyOnWriteArrayList<CountryMasterDesc>) saveObject.get("desc");
		
		LOGGER.info("size of the saver" +saveMasterList.size());
		LOGGER.info("size of the saveDescrList" +saveDescrList.size());
		int count = 0;
		
		try {
			for (CountryMaster master : saveMasterList) {
				save(master);
				for (int i = 0; i < 2; i++) {
					
					CountryMasterDesc dsec = saveDescrList.get(i);
					dsec.setFsCountryMaster(master);
					saveDescription(dsec);
					//saveDescrList.remove(dsec);
				}
				
				for (int j = 0; j < 2; j++) {

					saveDescrList.remove(count);
				}
				
			}
		} catch (Exception e) {
			LOGGER.info("Exception occured in save method" + e);
			throw new Exception(e);
		}
		
		LOGGER.info("Exit into save method");
	}

	@Override
	public List<CountryMasterDesc> viewById(BigDecimal countryId) {
		LOGGER.info("Entering into viewById  method");
		LOGGER.info("countryId - " +countryId);
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CountryMasterDesc.class, "countryMasterDesc");
		dCriteria.setFetchMode("countryMasterDesc.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("countryMasterDesc.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exit into viewById method");
		return (List<CountryMasterDesc>) findAll(dCriteria);
	}

	@Override
	public void delete(CountryMaster master, List<CountryMasterDesc> countryDescList) {
		
		LOGGER.info("Entering into delete method");
		for (CountryMasterDesc countryMasterDesc : countryDescList) {
			getSession().delete(countryMasterDesc);
			
		}
		getSession().delete(master);
		LOGGER.info("Exit into delete method");
	}

	@Override
	public List<CountryMasterDesc> getunApproveCountryList() {

		LOGGER.info("Entering into getunApproveCountryList method");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CountryMasterDesc.class, "countryMasterDesc");
		// Join Language Type table
		detachedCriteria.setFetchMode("countryMasterDesc.fsLanguageType", FetchMode.JOIN);
		detachedCriteria.createAlias("countryMasterDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		detachedCriteria.setFetchMode("countryMasterDesc.fsCountryMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("countryMasterDesc.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		detachedCriteria.add(Restrictions.eq("fsCountryMaster.isActive", "U"));
		detachedCriteria.addOrder(Order.asc("fsCountryMaster.countryId"));
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exit into getunApproveCountryList method");
		return (List<CountryMasterDesc>) findAll(detachedCriteria);
	
	}

	@Override
	public void approveRecord(BigDecimal countryId, String userName) {
		LOGGER.info("Entering into approveRecord method");
		CountryMaster country = (CountryMaster) getSession().get(CountryMaster.class, countryId);
		country.setIsActive(Constants.Yes);
		country.setCountryActive(Constants.Yes);
		country.setApprovedBy(userName);
		country.setApprovedDate(new Date());
		/*country.setIsActive("Y");
		country.setCountryActive("Y");
		country.setModifiedBy(userName);
		country.setModifiedDate(new Date());
		country.setApprovedBy(userName);
		country.setApprovedDate(new Date());*/
		getSession().update(country);
		LOGGER.info("Exit into approveRecord method");
		
	}

	@Override
	public List<CountryMaster> getCountryMasterListforApproval() {

		LOGGER.info("Entering into getCountryMasterList method");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CountryMaster.class, "countryMaster");
		detachedCriteria.add(Restrictions.eq("countryMaster.isActive", "U"));
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		detachedCriteria.addOrder(Order.asc("countryMaster.countryId"));
		LOGGER.info("Exit into getCountryMasterList method");
		return (List<CountryMaster>) findAll(detachedCriteria);
	
	}

	@Override
	public boolean TeleCodeCheck(String telCode) {
		LOGGER.info("Entering into TeleCodeCheck method");
		LOGGER.info("telCode -" +telCode);
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CountryMaster.class, "countryMaster");
		detachedCriteria.add(Restrictions.eq("countryMaster.countryTelCode", telCode));
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exit into TeleCodeCheck method");
		List<CountryMaster> l = (List<CountryMaster>) findAll(detachedCriteria);
		if (l.isEmpty())
			return false;
		return true;
	}

	@Override
	public List<CountryMasterDesc> getbankApplicabilityCountryList(BigDecimal languageId, List<BigDecimal> appCountryList) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CountryMasterDesc.class, "countryMasterDesc");
		dCriteria.setFetchMode("countryMasterDesc.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("countryMasterDesc.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.in("fsCountryMaster.countryId", appCountryList));
		dCriteria.setFetchMode("countryMasterDesc.fsLanguageType", FetchMode.JOIN);
		dCriteria.createAlias("countryMasterDesc.fsLanguageType", "languageType", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("languageType.languageId", languageId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exit into viewById method");
		return (List<CountryMasterDesc>) findAll(dCriteria);
	}
	

}
