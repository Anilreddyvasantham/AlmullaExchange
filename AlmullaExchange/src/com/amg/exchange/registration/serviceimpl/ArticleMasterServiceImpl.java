package com.amg.exchange.registration.serviceimpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.registration.dao.IArticleMastereDao;
import com.amg.exchange.registration.service.IArticleMasterservice;
import com.amg.exchange.treasury.model.ArticleMaster;
import com.amg.exchange.treasury.model.ArticleMasterDesc;

@Service("articleMasterservice")
public class ArticleMasterServiceImpl implements IArticleMasterservice {
	
	@Autowired
	IArticleMastereDao  articleMastereDao;

	@Override
	@Transactional
	public void save(ArticleMaster articleMaster) {
	
		articleMastereDao.save(articleMaster);
	}
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveOrUpdate(ArticleMaster articleMaster,ArticleMasterDesc articleMasterEngDesc,ArticleMasterDesc articleMasterArbDesc){
		articleMastereDao.saveOrUpdate(articleMaster,articleMasterEngDesc,articleMasterArbDesc);
	}
	@Override
	@Transactional
	public void saveDescription(ArticleMasterDesc articleMasterDesc) {
		articleMastereDao.saveDescription(articleMasterDesc);
		
	}

	@Override
	@Transactional
	public List<ArticleMasterDesc> viewAllRecords() {
		return articleMastereDao.viewAllRecords();
	}
 
	@Override
	@Transactional
	public List<String>  getAllComponent(String query) {
		return articleMastereDao.getAllComponent(query);
	}

	@Override
	@Transactional
	public ArticleMaster viewByCode(String articleCode) {
		
		return articleMastereDao.viewByCode(articleCode);
	}

	@Override
	@Transactional
	public List<ArticleMasterDesc> viewById(BigDecimal articleId) {
		return articleMastereDao.viewById(articleId);
	}

	@Override
	@Transactional
	public List<ArticleMaster> viewMasterRecords() {
		return articleMastereDao.viewMasterRecords();
	}

	@Override
	@Transactional
	public void delete(ArticleMaster articleMaster) {
		articleMastereDao.delete(articleMaster);
		
	}

	@Override
	@Transactional
	public void deleteDesc(ArticleMasterDesc articleMasterDesc) {
		articleMastereDao.deleteDesc(articleMasterDesc);
		
	}

	@Override
	@Transactional
	public List<ArticleMasterDesc> checkDesciption(String localArticleDescription) {
		// TODO Auto-generated method stub
		return articleMastereDao.checkDesciption(localArticleDescription);
	}

	@Override
	@Transactional
	public List<ArticleMaster> viewMasterRecords(BigDecimal countryId) {
		
		return articleMastereDao.viewMasterRecords(countryId);
	}


	@Override
	@Transactional
	public List<ArticleMaster> viewMasterRecordsforApproval(BigDecimal countryId) {
		 return articleMastereDao.viewMasterRecordsforApproval(countryId);
	}

	@Override
	public List<ArticleMasterDesc> getAllComponent(String query, BigDecimal countryId, BigDecimal languageId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveOrUpdate(ArticleMaster saveArticleMaster, Set desc) {
	
		articleMastereDao.saveOrUpdate(saveArticleMaster, desc);
	}

	

}
