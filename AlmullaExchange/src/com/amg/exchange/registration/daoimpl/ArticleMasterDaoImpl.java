package com.amg.exchange.registration.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.registration.dao.IArticleMastereDao;
import com.amg.exchange.treasury.model.ArticleMaster;
import com.amg.exchange.treasury.model.ArticleMasterDesc;
import com.amg.exchange.util.Constants;

@SuppressWarnings("unchecked")
@Repository
public class ArticleMasterDaoImpl<T> extends CommonDaoImpl<T> implements IArticleMastereDao, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = Logger.getLogger(ArticleMasterDaoImpl.class);

	@Override
	public void save(ArticleMaster articleMaster) {
		LOGGER.info("Entering into save method");
		getSession().saveOrUpdate(articleMaster);
		LOGGER.info("Exit into save method"); 
	}
	
	@Override
	public void saveOrUpdate(ArticleMaster articleMaster,ArticleMasterDesc articleMasterEngDesc,ArticleMasterDesc articleMasterArbDesc) {
		try{
			getSession().saveOrUpdate(articleMaster);
			getSession().saveOrUpdate(articleMasterEngDesc);
			getSession().saveOrUpdate(articleMasterArbDesc);
		}catch(Exception e){}
	}

	@Override
	public void saveDescription(ArticleMasterDesc articleMasterDesc) {
		LOGGER.info("Entering into saveDescription method");
		getSession().saveOrUpdate(articleMasterDesc);
		LOGGER.info("Exit into saveDescription method");
		
	}

	@Override
	public List<ArticleMasterDesc> viewAllRecords() {
		LOGGER.info("Entering into viewAllRecords method");
		DetachedCriteria dCriteria = DetachedCriteria.forClass(ArticleMasterDesc.class, "ArticleMasterDesc");
		dCriteria.setFetchMode("ArticleMasterDesc.articleMaster", FetchMode.JOIN);
		dCriteria.createAlias("ArticleMasterDesc.articleMaster", "articleMaster", JoinType.INNER_JOIN);
		dCriteria.setFetchMode("ArticleMasterDesc.languageType", FetchMode.JOIN);
		dCriteria.createAlias("ArticleMasterDesc.languageType", "languageType", JoinType.INNER_JOIN);
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exit into viewAllRecords method");
		return (List<ArticleMasterDesc>) findAll(dCriteria);
	}

	@Override
	public List<String>  getAllComponent(String query) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ArticleMaster.class, "articleMaster");
		criteria.add(Restrictions.like("articleMaster.articleCode", query, MatchMode.ANYWHERE).ignoreCase());
		 
		criteria.setProjection(Projections.property("articleMaster.articleCode"));
		criteria.addOrder(Order.asc("articleMaster.articleCode"));
		//criteria.add(Restrictions.eq("serviceMaster.isActive",  "N"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		 
		return (List<String>)findAll(criteria);
}

	@Override
	public ArticleMaster viewByCode(String articleCode) {
		LOGGER.info("Entering into viewByCode method");
		LOGGER.info("articleCode -" +articleCode);
		DetachedCriteria dCriteria = DetachedCriteria.forClass(ArticleMaster.class, "articleMaster");
		dCriteria.add(Restrictions.eq("articleMaster.articleCode", articleCode));
		List<ArticleMaster> list=(List<ArticleMaster>) findAll(dCriteria);
		
		if(list.isEmpty()) {
			LOGGER.info("Article code not found");
			return null;
			
		}
		LOGGER.info("Exit into viewByCode method");
		return list.get(0);
	}

	@Override
	public List<ArticleMasterDesc> viewById(BigDecimal articleId) {
		LOGGER.info("Entering into viewById method");
		LOGGER.info("articleId -" +articleId);
		DetachedCriteria dCriteria = DetachedCriteria.forClass(ArticleMasterDesc.class, "articleMasterDesc");
		dCriteria.setFetchMode("articleMasterDesc.articleMaster", FetchMode.JOIN);
		dCriteria.createAlias("articleMasterDesc.articleMaster", "articleMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("articleMaster.articleId", articleId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exit into viewById method");
		return (List<ArticleMasterDesc>) findAll(dCriteria);
	}

	@Override
	public List<ArticleMaster> viewMasterRecords() {
		LOGGER.info("Entering into viewMasterRecords method");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ArticleMaster.class, "ArticleMaster");
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exit into viewMasterRecords method");
		return (List<ArticleMaster>) findAll(detachedCriteria);
	}

	@Override
	public void delete(ArticleMaster articleMaster) {
		LOGGER.info("Entering into delete method");
		getSession().delete(articleMaster);
		LOGGER.info("Exit into delete method");
	}

	@Override
	public void deleteDesc(ArticleMasterDesc articleMasterDesc) {
		LOGGER.info("Entering into deleteDesc method");
		getSession().delete(articleMasterDesc);
		LOGGER.info("Exit into deleteDesc method");
		
	}


	@Override
	public List<ArticleMasterDesc> checkDesciption(String localArticleDescription) {
		LOGGER.info("Entering into checkDesciption method");
		LOGGER.info("localArticleDescription -" +localArticleDescription);
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ArticleMasterDesc.class, "articleMasterDesc");
		detachedCriteria.add(Restrictions.eq("articleMasterDesc.articleeDescription", localArticleDescription));
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exit into checkDesciption method");
		return (List<ArticleMasterDesc>) findAll(detachedCriteria);
	}

	@Override
	public List<ArticleMaster> viewMasterRecords(BigDecimal countryId) {
		LOGGER.info("Entering into viewMasterRecords method");
		LOGGER.info("localArticleDescription -" +countryId);
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ArticleMaster.class, "ArticleMaster");
		detachedCriteria.setFetchMode("ArticleMaster.fsCountryMaster",  FetchMode.SELECT);
		detachedCriteria.createAlias("ArticleMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		detachedCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		detachedCriteria.addOrder(Order.asc("ArticleMaster.articleCode"));
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exit into viewMasterRecords method");
		return (List<ArticleMaster>) findAll(detachedCriteria);
	}
/*
	@Override
	public List<ArticleMaster> getAllComponent(String query, BigDecimal countryId) {
		LOGGER.info("Entering into getAllComponent method");
		LOGGER.info("query -" +query);
		LOGGER.info("countryId -" +countryId);
		DetachedCriteria criteria = DetachedCriteria.forClass(ArticleMaster.class, "articleMaster");
		
		criteria.setFetchMode("articleMaster.fsCountryMaster",  FetchMode.SELECT);
		criteria.createAlias("articleMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		
		criteria.add(Restrictions.like("articleMaster.articleCode", query, MatchMode.ANYWHERE).ignoreCase());
		criteria.addOrder(Order.asc("articleMaster.articleCode"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exit into getAllComponent method");
		return (List<ArticleMaster>) findAll(criteria);
		
	}*/

	@Override
	public List<ArticleMaster> viewMasterRecordsforApproval(BigDecimal countryId) {

		LOGGER.info("Entering into viewMasterRecords method");
		LOGGER.info("localArticleDescription -" +countryId);
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ArticleMaster.class, "ArticleMaster");
		detachedCriteria.setFetchMode("ArticleMaster.fsCountryMaster",  FetchMode.SELECT);
		detachedCriteria.createAlias("ArticleMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		detachedCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		detachedCriteria.add(Restrictions.eq("ArticleMaster.isActive", Constants.U));
		detachedCriteria.addOrder(Order.asc("ArticleMaster.articleCode"));
		
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exit into viewMasterRecords method");
		return (List<ArticleMaster>) findAll(detachedCriteria);
	
	}

	@Override
	public void saveOrUpdate(ArticleMaster saveArticleMaster, Set desc) {
		LOGGER.info("Entering into saveOrUpdate method");
		save(saveArticleMaster);
		Iterator iter = desc.iterator();
		while (iter.hasNext()) {
		   saveDescription((ArticleMasterDesc)iter.next());
		}
		LOGGER.info("Exit into saveOrUpdate method");
	}


	}
