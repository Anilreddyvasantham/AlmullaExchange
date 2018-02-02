package com.amg.exchange.registration.dao;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.treasury.model.ArticleDetails;
import com.amg.exchange.treasury.model.ArticleDetailsDesc;
import com.amg.exchange.treasury.model.ArticleMasterDesc;

public interface IArticleLevelService {

	public List<ArticleMasterDesc> getArticles(BigDecimal languageId);

	public List<ArticleDetailsDesc> getAllArticleDetailList();

	public void saveArticleDetail(ArticleDetails articleDetails);

	public void saveArticleDetailDesc(ArticleDetailsDesc articleDetailsDesc);

	List<ArticleDetailsDesc> viewById(BigDecimal articledetailId);

	public ArticleDetails viewByCode(String articleDetailCode);

	public List<ArticleDetailsDesc> checkDesciption(
			String localArticleDescription);

	public List<ArticleDetails> viewMasterRecords();

	public void delete(ArticleDetails articleDetails);

	public void deleteDesc(ArticleDetailsDesc articleDetailsDesc);
	
	public String getArticleName(BigDecimal articleId , BigDecimal languageId);

}
