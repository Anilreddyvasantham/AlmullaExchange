package com.amg.exchange.registration.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import com.amg.exchange.treasury.model.ArticleMaster;
import com.amg.exchange.treasury.model.ArticleMasterDesc;



public interface  IArticleMasterservice {

	void save(ArticleMaster articleMaster);
	
	void saveOrUpdate(ArticleMaster articleMaster,ArticleMasterDesc articleMasterEngDesc,ArticleMasterDesc articleMasterArbDesc);

	void saveDescription(ArticleMasterDesc articleMasterDesc);

	List<ArticleMasterDesc> viewAllRecords();

	List<String> getAllComponent(String query);

	ArticleMaster viewByCode(String articleCode);

	List<ArticleMasterDesc> viewById(BigDecimal articleId);

	List<ArticleMaster> viewMasterRecords();

	void delete(ArticleMaster articleMaster);

	void deleteDesc(ArticleMasterDesc articleMasterDesc);

	List<ArticleMasterDesc> checkDesciption(String localArticleDescription);

	List<ArticleMaster> viewMasterRecords(BigDecimal countryId);

	List<ArticleMasterDesc> getAllComponent(String query, BigDecimal countryId, BigDecimal languageId);


	List<ArticleMaster> viewMasterRecordsforApproval(BigDecimal countryId);

	void saveOrUpdate(ArticleMaster saveArticleMaster, Set desc);
	
}
