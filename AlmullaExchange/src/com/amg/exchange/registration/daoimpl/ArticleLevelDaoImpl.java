package com.amg.exchange.registration.daoimpl;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.registration.dao.IArticleLevelDao;
import com.amg.exchange.treasury.model.ArticleDetails;
import com.amg.exchange.treasury.model.ArticleDetailsDesc;
import com.amg.exchange.treasury.model.ArticleMasterDesc;

@SuppressWarnings("unchecked")
@Repository
public class ArticleLevelDaoImpl<T> extends CommonDaoImpl<T> implements
		IArticleLevelDao {

	@Override
	public List<ArticleMasterDesc> getArticles(BigDecimal languageId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				ArticleMasterDesc.class, "articleMasterDesc");
		dCriteria.setFetchMode("articleMasterDesc.articleMaster",
					FetchMode.JOIN);
			dCriteria.createAlias("articleMasterDesc.articleMaster",
					"articleMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq(
				"articleMasterDesc.languageType.languageId", languageId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<ArticleMasterDesc>) findAll(dCriteria);
	}

	@Override
	public List<ArticleDetailsDesc> getAllArticleDetailList() {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				ArticleDetailsDesc.class, "articleDetailsDesc");
		dCriteria.setFetchMode("articleDetailsDesc.articleDetails",
				FetchMode.JOIN);
		dCriteria.createAlias("articleDetailsDesc.articleDetails",
				"articleDetails", JoinType.INNER_JOIN);
		dCriteria.setFetchMode("articleDetailsDesc.languageId", FetchMode.JOIN);
		dCriteria.createAlias("articleDetailsDesc.languageId", "languageId",
				JoinType.INNER_JOIN);
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<ArticleDetailsDesc> list = (List<ArticleDetailsDesc>) findAll(dCriteria);
		return list;
	}

	@Override
	public void saveArticleDetail(ArticleDetails articleDetails) {
		getSession().saveOrUpdate(articleDetails);
	}

	@Override
	public void saveArticleDetailDesc(ArticleDetailsDesc articleDetailsDesc) {
		getSession().saveOrUpdate(articleDetailsDesc);
	}

	@Override
	public List<ArticleDetailsDesc> viewById(BigDecimal articledetailId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				ArticleDetailsDesc.class, "articleDetailsDesc");
		dCriteria.setFetchMode("articleDetailsDesc.articleDetails",
				FetchMode.JOIN);
		dCriteria.createAlias("articleDetailsDesc.articleDetails",
				"articleMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("articleDetails.articleDetailId",
				articledetailId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<ArticleDetailsDesc>) findAll(dCriteria);
	}

	@Override
	public ArticleDetails viewByCode(String articleDetailCode) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				ArticleDetails.class, "articleDetails");
		dCriteria.add(Restrictions.eq("articleDetails.articleDetailCode",
				articleDetailCode));
		List<ArticleDetails> list = (List<ArticleDetails>) findAll(dCriteria);
		if (list.isEmpty())
			return null;
		return list.get(0);
	}

	@Override
	public List<ArticleDetailsDesc> checkDesciption(
			String localArticleDescription) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(
				ArticleDetailsDesc.class, "articleDetailsDesc");
		detachedCriteria.add(Restrictions.eq("articleDetailsDesc.articleDesc",
				localArticleDescription));
		detachedCriteria
				.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<ArticleDetailsDesc>) findAll(detachedCriteria);
	}

	@Override
	public List<ArticleDetails> viewMasterRecords() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(
				ArticleDetails.class, "articleDetails");
		detachedCriteria
				.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<ArticleDetails>) findAll(detachedCriteria);
	}

	@Override
	public void delete(ArticleDetails articleDetails) {
		getSession().delete(articleDetails);

	}

	@Override
	public void deleteDesc(ArticleDetailsDesc articleDetailsDesc) {
		getSession().delete(articleDetailsDesc);

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

}
