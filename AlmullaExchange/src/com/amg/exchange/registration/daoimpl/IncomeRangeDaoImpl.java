/**
 * 
 */
package com.amg.exchange.registration.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.registration.dao.IincomeRangeDao;
import com.amg.exchange.registration.model.IncomeRangeMaster;
import com.amg.exchange.treasury.model.ArticleDetails;
import com.amg.exchange.treasury.model.ArticleDetailsDesc;
import com.amg.exchange.treasury.model.ArticleMasterDesc;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.util.Constants;

/**
 * @author Subramaniam
 * 
 */
@SuppressWarnings("serial")
@Repository
public class IncomeRangeDaoImpl<T> extends CommonDaoImpl<T> implements IincomeRangeDao<T>, Serializable {

	// private static final Logger LOG =
	// Logger.getLogger(BranchPageDaoImpl.class);

	/**
	 * 
	 */
	public IncomeRangeDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void save(IncomeRangeMaster incomeRangeMaster) {

		getSession().saveOrUpdate(incomeRangeMaster);
	}

	@Override
	public void delete(IncomeRangeMaster incomeRangeMaster) {

		getSession().delete(incomeRangeMaster);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getArticleName(BigDecimal articleId , BigDecimal languageId) {
		System.out.println("get Artical name=====>");
		
		DetachedCriteria criteria = DetachedCriteria.forClass(ArticleMasterDesc.class, "articleMasterDesc");
		
		criteria.setFetchMode("articleMasterDesc.articleMaster", FetchMode.JOIN);
		criteria.createAlias("articleMasterDesc.articleMaster", "articleMaster", JoinType.INNER_JOIN);
		
		criteria.setFetchMode("articleMasterDesc.languageType", FetchMode.JOIN);
		criteria.createAlias("articleMasterDesc.languageType", "languageType", JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("languageType.languageId", languageId));

		criteria.add(Restrictions.eq("articleMaster.articleId", articleId));

		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		List<ArticleMasterDesc> data = (List<ArticleMasterDesc>) findAll(criteria);

		System.out.println("List Size ======= 1> " + data.size());

		if (data.isEmpty())
			return null;

		return data.get(0).getArticleeDescription();

	}

	@SuppressWarnings("unchecked")
	@Override
	public String getArticleDetailsName(BigDecimal articleDetailsId,BigDecimal languageId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(ArticleDetailsDesc.class, "articleDetailsDesc");

		criteria.setFetchMode("articleDetailsDesc.articleDetails", FetchMode.JOIN);
		criteria.createAlias("articleDetailsDesc.articleDetails", "articleDetails", JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("articleDetails.articleDetailId", articleDetailsId));
		
		criteria.setFetchMode("articleDetailsDesc.languageId", FetchMode.JOIN);
		criteria.createAlias("articleDetailsDesc.languageId", "languageId", JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("languageId.languageId", languageId));

		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		List<ArticleDetailsDesc> data = (List<ArticleDetailsDesc>) findAll(criteria);
		System.out.println("List Size ======= 2> " + data.size());
		if (data.isEmpty())
			return null;

		return data.get(0).getArticleDetailDesc();

	}

	@SuppressWarnings("unchecked")
	@Override
	public String getCountryCurrencyCode(BigDecimal countryId) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(CurrencyMaster.class, "currencyMaster");

		dCriteria.setFetchMode("currencyMaster.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("currencyMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<CurrencyMaster> data = (List<CurrencyMaster>) findAll(dCriteria);
		if (data.isEmpty())
			return null;

		return data.get(0).getQuoteName();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IncomeRangeMaster> getIncomeRangeList() {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(IncomeRangeMaster.class, "incomeRangeMaster");

		dCriteria.addOrder(Order.desc("incomeRangeMaster.incomeRangeId"));
		//dCriteria.add(Restrictions.eq("incomeRangeMaster.isActive", Constants.U));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<IncomeRangeMaster> data = (List<IncomeRangeMaster>) findAll(dCriteria);
		if (data.isEmpty())
			return null;

		return data;

	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<ArticleDetailsDesc> getArticleDetails(BigDecimal articleId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ArticleDetailsDesc.class, "articleDetailsDesc");

		criteria.setFetchMode("articleDetailsDesc.articleDetails", FetchMode.JOIN);
		criteria.createAlias("articleDetailsDesc.articleDetails", "articleDetails", JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("articleDetailsDesc.articleDetailsDescId", articleId));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<ArticleDetailsDesc> data = (List<ArticleDetailsDesc>) findAll(criteria);

		System.out.println("List Size ========= > " + data.size());

		if (data.isEmpty())
			return null;

		return data;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<IncomeRangeMaster> getIncomeRange(BigDecimal incomeRangeId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(IncomeRangeMaster.class, "incomeRangeMaster");

		criteria.add(Restrictions.eq("incomeRangeMaster.incomeRangeId", incomeRangeId));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<IncomeRangeMaster> data = (List<IncomeRangeMaster>) findAll(criteria);

		if (data.isEmpty())
			return null;

		return data;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<ArticleDetailsDesc> getArticleDetailsDesc(BigDecimal articleId, BigDecimal languageId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ArticleDetailsDesc.class, "articleDetailsDesc");

		criteria.setFetchMode("articleDetailsDesc.articleDetails", FetchMode.JOIN);
		criteria.createAlias("articleDetailsDesc.articleDetails", "articleDetails", JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("articleDetails.articleDetailId", articleId));
		
		criteria.setFetchMode("articleDetailsDesc.languageId", FetchMode.JOIN);
		criteria.createAlias("articleDetailsDesc.languageId", "languageId", JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("languageId.languageId", languageId));


		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<ArticleDetailsDesc> data = (List<ArticleDetailsDesc>) findAll(criteria);

		System.out.println("List Size ========= > " + data.size());

		if (data.isEmpty())
			return null;

		return data;
	}
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<ArticleDetails> getArticleId(BigDecimal articleDetailId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ArticleDetails.class, "articleDetails");

		criteria.setFetchMode("articleDetails.fsArticleMaster", FetchMode.JOIN);
		criteria.createAlias("articleDetails.fsArticleMaster", "fsArticleMaster", JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("articleDetails.articleDetailId", articleDetailId));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<ArticleDetails> data = (List<ArticleDetails>) findAll(criteria);

		System.out.println("List Size ========= > " + data.size());

		if (data.isEmpty())
			return null;

		return data;
	}
	/*
	@SuppressWarnings({ "unchecked" })
	@Override
	public boolean isExistIncomeRange_OLD(BigDecimal appCountryId,BigDecimal articleDetailId,BigDecimal fromAmount, BigDecimal toAmount) {

		DetachedCriteria criteria = DetachedCriteria.forClass(IncomeRangeMaster.class, "incomeRangeMaster");
		
		criteria.setFetchMode("incomeRangeMaster.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("incomeRangeMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("fsCountryMaster.countryId", appCountryId));
		
		criteria.setFetchMode("incomeRangeMaster.articleDetail", FetchMode.JOIN);
		criteria.createAlias("incomeRangeMaster.articleDetail", "articleDetail", JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("articleDetail.articleDetailId", articleDetailId));		
		
		criteria.add(Restrictions.or(Restrictions.ge("incomeRangeMaster.incomeRangeFrom", fromAmount),Restrictions.le("incomeRangeMaster.incomeRangeTo", toAmount)));
	

		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<IncomeRangeMaster> data = (List<IncomeRangeMaster>) findAll(criteria);
		
		System.out.println(""+data.size());

		if (data.isEmpty())
			return true;

		return false;
	}
	*/
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public boolean isExistIncomeRange(BigDecimal appCountryId,BigDecimal articleDetailId,BigDecimal fromAmount, BigDecimal toAmount) {
	
		//String queryString = "SELECT count(*) FROM FS_INCOME_RANGE_MASTER WHERE APPLICATION_COUNTRY_ID =? AND ARTICLE_DETAIL_ID = ? AND ( (? between income_from and income_to) or (? between income_from and income_to))";
		List<Integer> objList = new ArrayList<Integer>();
		String queryString = "SELECT * FROM FS_INCOME_RANGE_MASTER WHERE APPLICATION_COUNTRY_ID =? AND ARTICLE_DETAIL_ID = ? AND ( (? between income_from and income_to) or (? between income_from and income_to))";
		
		objList = getSession().createSQLQuery(queryString).setBigDecimal(0, appCountryId).setBigDecimal(1, articleDetailId).setBigDecimal(2, fromAmount).setBigDecimal(3, toAmount).list();
	
		if(!objList.isEmpty()) {
		return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IncomeRangeMaster> getIncomeFRangeListforDetail(BigDecimal countryId, BigDecimal articleDetailId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(IncomeRangeMaster.class, "incomeRangeMaster");
		criteria.setFetchMode("incomeRangeMaster.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("incomeRangeMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		criteria.setFetchMode("incomeRangeMaster.articleDetail", FetchMode.JOIN);
		criteria.createAlias("incomeRangeMaster.articleDetail", "articleDetail", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("articleDetail.articleDetailId", articleDetailId));
		return (List<IncomeRangeMaster>) findAll(criteria);
	}

}
