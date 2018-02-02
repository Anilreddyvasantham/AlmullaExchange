package com.amg.exchange.registration.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.registration.dao.IArticleLevelDao;
import com.amg.exchange.registration.dao.IArticleLevelService;
import com.amg.exchange.treasury.model.ArticleDetails;
import com.amg.exchange.treasury.model.ArticleDetailsDesc;
import com.amg.exchange.treasury.model.ArticleMasterDesc;

@SuppressWarnings("serial")
@Service
public class ArticleLevelServiceImpl implements IArticleLevelService, Serializable {
	
	@Autowired
	IArticleLevelDao articleLevelDao;
	
	
	@Override
	@Transactional
	public List<ArticleDetailsDesc> getAllArticleDetailList(){
		return articleLevelDao.getAllArticleDetailList();
	}
	
	@Override
	@Transactional
	public List<ArticleMasterDesc> getArticles(BigDecimal languageId){
		return articleLevelDao.getArticles(languageId);
	}
	
	@Override
	@Transactional
	public void saveArticleDetail(ArticleDetails articleDetails) {
		articleLevelDao.saveArticleDetail(articleDetails);
		
	}
	

	@Override
	@Transactional
	public void saveArticleDetailDesc(ArticleDetailsDesc articleDetailsDesc) {
		articleLevelDao.saveArticleDetailDesc(articleDetailsDesc);
		
	}
	
	@Override
	@Transactional
	public List<ArticleDetailsDesc> viewById(BigDecimal articleDetailId) {
		return articleLevelDao.viewById(articleDetailId);
	}
	
	@Override
	@Transactional
	public ArticleDetails viewByCode(String articleDetailCode){
		return articleLevelDao.viewByCode(articleDetailCode);
	}
	
	@Override
	@Transactional
	public List<ArticleDetailsDesc> checkDesciption(String localArticleDescription){
		return articleLevelDao.checkDesciption(localArticleDescription);
	}
	
	@Override
	@Transactional
	public List<ArticleDetails> viewMasterRecords() {
		
		return articleLevelDao.viewMasterRecords();
	}
	
	@Override
	@Transactional
	public void delete(ArticleDetails articleDetails){
		articleLevelDao.delete(articleDetails);
	}
	
	@Override
	@Transactional
	public void deleteDesc(ArticleDetailsDesc articleDetailsDesc){
		articleLevelDao.deleteDesc(articleDetailsDesc);
	}
	@Override
	@Transactional
	public String getArticleName(BigDecimal articleId , BigDecimal languageId) {
		
		return articleLevelDao.getArticleName(articleId,languageId);
	}
}
